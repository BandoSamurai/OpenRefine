/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.google.refine.expr.functions.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.refine.expr.EvalError;
import com.google.refine.grel.ControlFunctionRegistry;
import com.google.refine.grel.Function;
import com.google.refine.util.JSONUtilities;

public class Sort implements Function {

    @Override
    @SuppressWarnings("unchecked")
    public Object call(Properties bindings, Object[] args) {
        if (args.length == 1) {
            Object v = args[0];
            
            if (v != null) {
                if (v.getClass().isArray()) {
                    Object[] a = (Object[]) v;
                    Object[] r = a.clone();
                    
                    Arrays.sort(r, 0, r.length);
                    
                    return r;
                } else if (v instanceof JSONArray) {
                    try {
                        Object[] r = JSONUtilities.toArray((JSONArray) v);
                        
                        Arrays.sort(r, 0, r.length);
                        
                        return r;
                    } catch (JSONException e) {
                        return new EvalError(ControlFunctionRegistry.getFunctionName(this) +
                                " fails to process a JSON array: " + e.getMessage());
                    }
                } else if (v instanceof List<?>) {
                    List<? extends Comparable<Object>> a = (List<? extends Comparable<Object>>) v;
                    Collections.sort(a);
                    
                    return a;
                }
            }
        }
        return new EvalError(ControlFunctionRegistry.getFunctionName(this) + " expects an array");
    }

    @Override
    public String getDescription() {
        return "Sorts array a";
    }
    
    @Override
    public String getParams() {
        return "array a";
    }
    
    @Override
    public String getReturns() {
        return "array";
    }
}
