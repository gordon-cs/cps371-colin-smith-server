/**
 * Adapted from 2 sources by Russ Tuck, 2016:
 *  - Google App Engine "helloworld" example app.
 *  - http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
 */
/**
 * Copyright 2015 Google Inc. All Rights Reserved.
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

package edu.gordon.cs.examples371;

// Client side:
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// Server side:
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WeatherProxy extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    resp.setHeader("content-type", "application/json; charset=utf-8");
    PrintWriter out = resp.getWriter();
    this.getWeather();
    //TODO: handle JSONP requests properly.
    out.println(weatherNow);
  }

  /* Hold cached weather data in JSON format from forecast.io.
   * Note that it contains a timestamp indicating when it was fetched.
   */
  private String weatherNow = "";

  private void getWeather() {
    String apiUrl = "https://api.forecast.io/forecast/";
    String apiKey = "14e723fbe931ee119ade496aabcf28ba";
    String wenhamLat = "42.589611";
    String wenhamLon = "-70.819806";
    String urlString = apiUrl + apiKey + "/" + wenhamLat + "," + wenhamLon;

    try {
      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.connect();

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + conn.getResponseCode());
      }

      // The body of the response is available as an input stream.
      // Using BufferedReader is good practice for efficient I/O, because it
      // takes lots of little reads and does fewer larger actual I/O
      // operations.  It doesn't make much difference in this case,
      // since we only do one I/O.  But it's still good practice.
      BufferedReader br = new BufferedReader(new InputStreamReader(
                                               (conn.getInputStream())));
      // api.forecast.io returns a single (very long) line of JSON.
      weatherNow += br.readLine();

    } catch (MalformedURLException e) {
      e.printStackTrace();
      this.weatherNow = "{ 'error': 'MalformedURLException' }";
    } catch (IOException e) {
      e.printStackTrace();
      this.weatherNow = "{ 'error': 'IOException' }";
    }
  }
}
