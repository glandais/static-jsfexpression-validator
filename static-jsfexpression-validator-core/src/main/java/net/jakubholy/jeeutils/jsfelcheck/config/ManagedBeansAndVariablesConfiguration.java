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

package net.jakubholy.jeeutils.jsfelcheck.config;

import net.jakubholy.jeeutils.jsfelcheck.beanfinder.FileUtils;
import net.jakubholy.jeeutils.jsfelcheck.validator.FakeValueFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import static net.jakubholy.jeeutils.jsfelcheck.util.ArgumentAssert.assertNotNull;

/**
 * Configuration of where should be definitions of known managed beans loaded from
 * and optionally of global (as opposed to tag-local) variables that the validator cannot detect
 * itself.
 * (Basically a managed bean is also just a variable.)
 * <p>
 * You will typically want to static import the static methods to make configurations more readable.
 * </p>
 * <p/>
 * <h3>Usage example</h3>
 * {@code
 * import static net.jakubholy.jeeutils.jsfelcheck.config.ManagedBeansAndVariablesConfiguration.*;
 * <p/>
 * fromFacesConfigFiles("myBean.collectionProperty", String.class)
 * .andFromSpringConfigFiles("myBean.anotherArray", MyArrayElement.class)
 * .withExtraVariable("name", MyType.class)
 * .withExtraVariable("another", String.class)
 * }
 */
public class ManagedBeansAndVariablesConfiguration {

    private Collection<InputStream> facesConfigStreams = Collections.emptyList();
    private Collection<InputStream> springConfigStreams = Collections.emptyList();
    private final Map<String, Object> extraVariables = new Hashtable<String, Object>();

    /**
     * New configuration set to read managed beans from the given faces-config.xml files. (Default: none.)
     * Set to empty or null not to process any.
     * @param facesConfigFiles (optional) faces-config files to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromFacesConfigFiles(File... facesConfigFiles) {
        Collection<File> fileList = (facesConfigFiles == null)? null : Arrays.asList(facesConfigFiles);
        return new ManagedBeansAndVariablesConfiguration().andFromFacesConfigFiles(fileList);
    }

    /**
     * New configuration set to read managed beans from the given faces-config.xml files. (Default: none.)
     * Set to empty or null not to process any.
     * @param facesConfigFiles (optional) faces-config files to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromFacesConfigFiles(Collection<File> facesConfigFiles) {
        return new ManagedBeansAndVariablesConfiguration().andFromFacesConfigFiles(facesConfigFiles);
    }

    /**
     * New configuration set to read managed beans from the given faces-config.xml files. (Default: none.)
     * Set to empty or null not to process any.
     * @param facesConfigStreams (optional) faces-config file streams to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromFacesConfigStreams(Collection<InputStream> facesConfigStreams) {
        return new ManagedBeansAndVariablesConfiguration().andFromFacesConfigStreams(facesConfigStreams);
    }

    /**
     * New configuration set to read managed beans from the given Spring application context XML files.
     * (Default: none.)
     * Set to empty or null not to process any.
     * @param springConfigFiles (optional) Spring applicationContext files to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromSpringConfigFiles(File... springConfigFiles) {
        Collection<File> fileList = (springConfigFiles == null)? null : Arrays.asList(springConfigFiles);
        return new ManagedBeansAndVariablesConfiguration().andFromSpringConfigFiles(fileList);
    }

    /**
     * New configuration set to read managed beans from the given Spring application context XML files.
     * (Default: none.)
     * Set to empty or null not to process any.
     * @param springConfigFiles (optional) Spring applicationContext files to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromSpringConfigFiles(Collection<File> springConfigFiles) {
        return new ManagedBeansAndVariablesConfiguration().andFromSpringConfigFiles(springConfigFiles);
    }

    /**
     * New configuration set to read managed beans from the given Spring application context XML files.
     * (Default: none.)
     * Set to empty or null not to process any.
     * @param springConfigStreams (optional) Spring applicationContext file streams to read managed beans from; may be empty or null
     */
    public static ManagedBeansAndVariablesConfiguration fromSpringConfigStreams(Collection<InputStream> springConfigStreams) {
        return new ManagedBeansAndVariablesConfiguration().andFromSpringConfigStreams(springConfigStreams);
    }

    /**
     * Create new. empty configuration - used if you only want to define extra variables.
     * @see #withExtraVariable(String, Class)
     */
    public static ManagedBeansAndVariablesConfiguration forExtraVariables() {
        return new ManagedBeansAndVariablesConfiguration();
    }

    // #########################################################################################################

    // -------------------------------------------------------------------------------------------------- Faces

    /**
     * Non-static version of {@link #fromFacesConfigFiles(java.util.Collection)}.
     */
    public ManagedBeansAndVariablesConfiguration andFromFacesConfigFiles(Collection<File> facesConfigFiles) {
        Collection<InputStream> streams = (facesConfigFiles == null) ? null : FileUtils.filesToStream(facesConfigFiles);
        return andFromFacesConfigStreams(streams);
    }

    /**
     * Non-static version of {@link #fromFacesConfigStreams(java.util.Collection)}}.
     */
    public ManagedBeansAndVariablesConfiguration andFromFacesConfigStreams(Collection<InputStream> facesConfigStreams) {
        if (facesConfigStreams == null || facesConfigStreams.isEmpty()) {
            this.facesConfigStreams = Collections.emptyList();
        } else {
            this.facesConfigStreams = facesConfigStreams;
        }
        return this;
    }

    /** internal use only */
    public Collection<InputStream> getFacesConfigStreams() {
        return Collections.unmodifiableCollection(facesConfigStreams);
    }

    // -------------------------------------------------------------------------------------------------- Spring

    /**
     * Non-static version of {@link #fromSpringConfigFiles(java.util.Collection)}.
     */

    public ManagedBeansAndVariablesConfiguration andFromSpringConfigFiles(Collection<File> springConfigFiles) {
        Collection<InputStream> streams = (springConfigFiles == null) ? null : FileUtils.filesToStream(springConfigFiles);
        return andFromSpringConfigStreams(streams);
    }

    /**
     * Non-static version of {@link #fromSpringConfigStreams(java.util.Collection)}.
     */
    public ManagedBeansAndVariablesConfiguration andFromSpringConfigStreams(Collection<InputStream> springConfigStreams) {
        if (springConfigStreams == null || springConfigStreams.isEmpty()) {
            this.springConfigStreams = Collections.emptyList();
        } else {
            this.springConfigStreams = springConfigStreams;
        }
        return this;
    }

    /**
     * internal use only
     */
    public Collection<InputStream> getSpringConfigStreams() {
        return Collections.unmodifiableCollection(springConfigStreams);
    }

    // ------------------------------------------------------------------------------------------------- Extra variables


    /**
     * Register a EL variable and its value so that when it encountered in an EL expression, it will be possible to
     * resolve it.
     * Normally the {@link net.jakubholy.jeeutils.jsfelcheck.validator.exception.VariableNotFoundException}
     * is thrown when an undeclared/unknown variable in encountered.
     * <p>You most likely actually want to use {@link #withExtraVariable(String, Class)} as passing an actual
     * value has rarely any benefits.
     * </p>
     * You use this typically to declare managed beans and their value.
     * The purpose of this method is to make it possible to declare variables of types that whose value we
     * currently cannot fake.
     *
     * @param name  (required) the name of the EL variable (i.e. the first identifier in any EL expression:
     *              var.prop1.prop2)
     * @param value (required) the value to be returned for the variable, used in further evaluation. WARNING: It should
     *              be an actual instance, not a Class!
     * @return this
     * @see #withExtraVariable(String, Class)
     */
    public ManagedBeansAndVariablesConfiguration withExtraVariable(final String name, final Object value) {
        assertNotNull(name, "name", String.class);
        assertNotNull(value, "value", Object.class);
        extraVariables.put(name, value);
        return this;
    }

    /**
     * Register a EL variable and its value so that when it encountered in an EL expression, it will be possible to
     * resolve it.
     * Normally the {@link net.jakubholy.jeeutils.jsfelcheck.validator.exception.VariableNotFoundException}
     * is thrown when an undeclared/unknown variable in encountered.
     * You use this typically to declare managed beans and their class.
     * <p>
     * For the puropose of validation a fake value of the type is created using
     * {@link FakeValueFactory#fakeValueOfType(Class, Object)}.
     * </p>
     *
     * @param name      (required) the name of the EL variable (i.e. the first identifier in any EL expression:
     *                  var.prop1.prop2)
     * @param valueType (required) the value to be returned for the variable, used in further evaluation.
     * @return this
     * @see #withExtraVariable(String, Object)
     */
    public ManagedBeansAndVariablesConfiguration withExtraVariable(final String name, final Class valueType) {
        assertNotNull(name, "name", String.class);
        assertNotNull(valueType, "value", Object.class);
        Object fakeValue = FakeValueFactory.fakeValueOfType(valueType, name);
        extraVariables.put(name, fakeValue);
        return this;
    }

    /**
     * internal use only
     */
    public Map<String, Object> getExtraVariables() {
        return Collections.unmodifiableMap(extraVariables);
    }
}
