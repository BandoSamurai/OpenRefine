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

package com.google.refine.operations.column;

import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.refine.history.HistoryEntry;
import com.google.refine.model.AbstractOperation;
import com.google.refine.model.Project;
import com.google.refine.model.changes.ColumnReorderChange;
import com.google.refine.util.ParsingUtilities;

public class ColumnReorderOperation extends AbstractOperation {
    static public AbstractOperation reconstruct(Project project, JSONObject obj) throws Exception {
        return ParsingUtilities.mapper.readValue(obj.toString(), ColumnReorderOperation.class);
    }
    
    final protected List<String> _columnNames;
    
    @JsonCreator
    public ColumnReorderOperation(
            @JsonProperty("columnNames")
            List<String> columnNames) {
        _columnNames = columnNames;
    }
    
    @JsonProperty("columnNames")
    public List<String> getColumnNames() {
        return _columnNames;
    }

    @Override
    protected String getBriefDescription(Project project) {
        return "Reorder columns";
    }

   @Override
protected HistoryEntry createHistoryEntry(Project project, long historyEntryID) throws Exception {
        return new HistoryEntry(
            historyEntryID,
            project, 
            "Reorder columns", 
            this, 
            new ColumnReorderChange(_columnNames)
        );
    }
}
