/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package aiplatform;

// [START aiplatform_sdk_ideation]
// [START generativeaionvertexai_sdk_ideation]

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PredictTextPromptSample {

  public static void main(String[] args) throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    // Details of designing text prompts for supported large language models:
    // https://cloud.google.com/vertex-ai/docs/generative-ai/text/text-overview
    String instance =
        "{ \"prompt\": " + "\"Give me ten interview questions for the role of program manager.\"}";
    String parameters =
        "{\n"
            + "  \"temperature\": 0.2,\n"
            + "  \"maxOutputTokens\": 256,\n"
            + "  \"topP\": 0.95,\n"
            + "  \"topK\": 40\n"
            + "}";
    String project = "YOUR_PROJECT_ID";
    String location = "us-central1";
    String publisher = "google";
    String model = "text-bison@001";

    predictTextPrompt(instance, parameters, project, location, publisher, model);
  }

  // Get a text prompt from a supported text model
  public static void predictTextPrompt(
      String instance,
      String parameters,
      String project,
      String location,
      String publisher,
      String model)
      throws IOException {
    String endpoint = String.format("%s-aiplatform.googleapis.com:443", location);
    PredictionServiceSettings predictionServiceSettings =
        PredictionServiceSettings.newBuilder().setEndpoint(endpoint).build();

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.
    try (PredictionServiceClient predictionServiceClient =
        PredictionServiceClient.create(predictionServiceSettings)) {
      final EndpointName endpointName =
          EndpointName.ofProjectLocationPublisherModelName(project, location, publisher, model);

      // Initialize client that will be used to send requests. This client only needs to be created
      // once, and can be reused for multiple requests.
      Value.Builder instanceValue = Value.newBuilder();
      JsonFormat.parser().merge(instance, instanceValue);
      List<Value> instances = new ArrayList<>();
      instances.add(instanceValue.build());

      // Use Value.Builder to convert instance to a dynamically typed value that can be
      // processed by the service.
      Value.Builder parameterValueBuilder = Value.newBuilder();
      JsonFormat.parser().merge(parameters, parameterValueBuilder);
      Value parameterValue = parameterValueBuilder.build();

      PredictResponse predictResponse =
          predictionServiceClient.predict(endpointName, instances, parameterValue);
      System.out.println("Predict Response");
      System.out.println(predictResponse);
    }
  }
}
// [END aiplatform_sdk_ideation]
// [END generativeaionvertexai_sdk_ideation]
