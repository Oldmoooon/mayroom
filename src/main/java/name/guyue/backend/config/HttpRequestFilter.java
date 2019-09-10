package name.guyue.backend.config;

import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author hujia
 * @date 2019-05-22
 */
@Component
public class HttpRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            var requestWrapper = new JsonServletRequestWrapper(request);
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Slf4j
    private static class JsonServletRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> parameterMap;
        private byte[] bytes;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        JsonServletRequestWrapper(HttpServletRequest request) {
            super(request);
            parameterMap = request.getParameterMap();
            if (parameterMap == null || parameterMap.isEmpty()) {
                JsonObject body = null;
                try {
                    bytes = ByteStreams.toByteArray(request.getInputStream());
                    if (bytes.length != 0) {
                        body = new JsonParser().parse(new String(bytes)).getAsJsonObject();
                    }
                } catch (IOException e) {
                    log.error("io exception when read request input stream.", e);
                }
                if (body != null && !body.isJsonNull()) {
                    parameterMap = Maps.newHashMap();
                    body.entrySet().forEach(e -> {
                        String[] value;
                        if (e.getValue().isJsonArray()) {
                            JsonArray array = e.getValue().getAsJsonArray();
                            value = new String[array.size()];
                            for (int i = 0; i < array.size(); i++) {
                                value[i] = array.get(i).getAsString();
                            }
                        } else {
                            value = new String[]{e.getValue().getAsString()};
                        }
                        parameterMap.put(e.getKey(), value);
                    });
                }
            }
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return parameterMap;
        }

        @Override
        public String getParameter(String name) {
            String[] value = parameterMap.get(name);
            return value != null && value.length > 0 ? value[0] : "";
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Vector<>(parameterMap.keySet()).elements();
        }

        @Override
        public String[] getParameterValues(String name) {
            return parameterMap.get(name);
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream data = new ByteArrayInputStream(bytes);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return data.available() <= 0;
                }

                @Override
                public boolean isReady() {
                    return data.available() > 0;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() {
                    return data.read();
                }
            };
        }
    }
}
