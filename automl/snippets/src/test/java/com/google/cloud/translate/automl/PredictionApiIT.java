/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.translate.automl;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for translation "PredictionAPI" sample. */
@RunWith(JUnit4.class)
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public class PredictionApiIT {
  private static final String COMPUTE_REGION = "us-central1";
  private static final String PROJECT_ID = "java-docs-samples-testing";
  private static final String modelId = "TRL2188848820815848149";
  private static final String filePath = "./resources/input.txt";
  private ByteArrayOutputStream bout;
  private PrintStream out;
  private PrintStream originalPrintStream;

  @Before
  public void setUp() {
    bout = new ByteArrayOutputStream();
    out = new PrintStream(bout);
    originalPrintStream = System.out;
    System.setOut(out);
  }

  @After
  public void tearDown() {
    // restores print statements in the original method
    System.out.flush();
    System.setOut(originalPrintStream);
  }

  @Test
  public void testPredict() throws Exception {
    // Act
    PredictionApi.predict(PROJECT_ID, COMPUTE_REGION, modelId, filePath);

    // Assert
    String got = bout.toString();
    assertThat(got).contains("Translated Content");
  }
}