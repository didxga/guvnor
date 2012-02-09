/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.drools.guvnor.server.converters.decisiontable.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to convert XLS Decision Table parameters from their "$..."
 * format to the "@{...}" format required by the Templating mechanism used with
 * the BRL editors and related DRL generators. Template Keys must be unique
 * across the entire model.
 */
public class ParameterUtilities {

    private static final Pattern patternSingleParameter  = Pattern.compile( "\\$param" );

    private static final Pattern patternIndexedParameter = Pattern.compile( "\\$\\d+" );

    private static final Pattern patternTemplateKey      = Pattern.compile( "@\\{.+?\\}" );

    private static final String  PARAMETER_PREFIX        = "param";

    private int                  parameterCounter        = 1;

    public String convertIndexedParametersToTemplateKeys(String xlsTemplate) {
        //Replace indexed parameter place-holders
        StringBuffer result = new StringBuffer();
        final Matcher matcherIndexedParameter = patternIndexedParameter.matcher( xlsTemplate );
        while ( matcherIndexedParameter.find() ) {
            matcherIndexedParameter.appendReplacement( result,
                                                       "@{" + PARAMETER_PREFIX + (parameterCounter++) + "}" );
        }
        matcherIndexedParameter.appendTail( result );
        return result.toString();
    }

    public String convertSingleParameterToTemplateKey(String xlsTemplate) {
        //Replace the single parameter place-holder
        StringBuffer result = new StringBuffer();
        final Matcher matcherSingleParameter = patternSingleParameter.matcher( xlsTemplate );
        if ( matcherSingleParameter.find() ) {
            matcherSingleParameter.appendReplacement( result,
                                                      "@{" + PARAMETER_PREFIX + (parameterCounter++) + "}" );
        }
        matcherSingleParameter.appendTail( result );
        return result.toString();
    }

    public List<String> extractTemplateKeys(String template) {
        //Extract Template Keys
        List<String> result = new ArrayList<String>();
        final Matcher matcherTemplateKey = patternTemplateKey.matcher( template );
        while ( matcherTemplateKey.find() ) {
            String fullKey = matcherTemplateKey.group();
            result.add( fullKey.substring( 2,
                                           fullKey.length() - 1 ) );
        }
        return result;
    }

}
