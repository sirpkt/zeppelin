/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zeppelin.server;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cors filter
 *
 */
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
      HttpServletResponse resp = ((HttpServletResponse) response);
      addCorsHeaders(resp);
      return;
    }

    if (response instanceof HttpServletResponse) {
      HttpServletResponse alteredResponse = ((HttpServletResponse) response);
      addCorsHeaders(alteredResponse);
    }
    filterChain.doFilter(request, response);
  }

  private void addCorsHeaders(HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Headers", "authorization,Content-Type");
    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, HEAD, DELETE");
    DateFormat fullDateFormatEN =
        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("EN", "en"));
    response.addHeader("Date", fullDateFormatEN.format(new Date()));
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}
}
