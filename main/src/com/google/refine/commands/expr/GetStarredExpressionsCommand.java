package com.google.refine.commands.expr;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONWriter;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.refine.Jsonizable;
import com.google.refine.ProjectManager;
import com.google.refine.commands.Command;
import com.google.refine.preference.TopList;


public class GetStarredExpressionsCommand extends Command {
    
    protected static class Expression implements Jsonizable {
        @JsonProperty("code")
        protected String code;
        protected Expression(String c) {
            code = c;
        }
        @Override
        public void write(JSONWriter writer, Properties options)
                throws JSONException {
            writer.object();
            writer.key("code"); writer.value(code);
            writer.endObject();
        }
    }
    
    protected static class ExpressionList implements Jsonizable {
        @JsonProperty("expressions")
        protected List<Expression> expressions;
        protected ExpressionList(List<Expression> e) {
            expressions = e;
        }
        @Override
        public void write(JSONWriter writer, Properties options)
                throws JSONException {
            writer.object();
            writer.key("expressions");
                writer.array();
                for (Expression s : expressions) {
                    s.write(writer, options);
                }
                writer.endArray();
            writer.endObject();
        }
    }
    
    public static ExpressionList getExpressionsList() {
        List<String> starredExpressions = ((TopList)ProjectManager.singleton.getPreferenceStore().get("scripting.starred-expressions")).getList();
        return new ExpressionList(starredExpressions.stream().map(e -> new Expression(e)).collect(Collectors.toList()));
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            respondJSON(response, getExpressionsList());
        } catch (Exception e) {
            respondException(response, e);
        }
    }
}
