/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakubholy.jeeutils.jsfelcheck;

import net.jakubholy.jeeutils.jsfelcheck.beanfinder.ManagedBeanFinder;
import net.jakubholy.jeeutils.jsfelcheck.beanfinder.jsf12.Jsf12FacesConfigXmlBeanFinder;
import net.jakubholy.jeeutils.jsfelcheck.validator.ValidatingElResolver;
import net.jakubholy.jeeutils.jsfelcheck.validator.jsf12.Jsf12ValidatingElResolver;
import org.apache.el.parser.AstAnd;

import java.io.InputStream;
import java.util.Collection;

/**
 * {@inheritDoc}
 *
 * Implementation based on JSF 1.2.
 */
public class JsfStaticAnalyzer extends AbstractJsfStaticAnalyzer {

    public static void main(String[] args) throws Exception { // SUPPRESS CHECKSTYLE (no javadoc)
        AbstractJsfStaticAnalyzer.main(new JsfStaticAnalyzer(), args);
    }

    @Override
    protected ValidatingElResolver createValidatingElResolver() {
        if (! new AstAnd(0).toString().startsWith("HACKED BY JSFELCHECK ")) {
            handleUnhackedElImplementationLoaded("jasper-el");  // JSF 2.0: tomcat-jasper-el
        }
        return new Jsf12ValidatingElResolver();
    }

    @Override
    protected ManagedBeanFinder createManagedBeanFinder(
            Collection<InputStream> facesConfigFilesToRead) {
        return Jsf12FacesConfigXmlBeanFinder.forStreams(facesConfigFilesToRead);
    }

}
