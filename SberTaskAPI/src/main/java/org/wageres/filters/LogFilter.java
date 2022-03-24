package org.wageres.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.wageres.model.RequestLog;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter
{
    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

    @Override
    public void destroy(){}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest)(request);
        String reqAction = req.getMethod();
        String ipAddress = req.getHeader("X-FORWARDED-FOR");

        String reqUrl = req.getRequestURL().toString();
        if(reqUrl.contains("/api"))
        {
            reqUrl = reqUrl.split("/api",2)[1];
        }

        if (ipAddress == null)
        {
            ipAddress = req.getRemoteAddr();
        }
        long start = System.currentTimeMillis();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        String RequestDate = dateFormat.format(date);

        chain.doFilter(request,response);

        long reqTime = System.currentTimeMillis() - start;

        HttpServletResponse resp = (HttpServletResponse)(response);
        int status = resp.getStatus();

        RequestLog logs = new RequestLog(reqTime,reqAction,ipAddress,reqUrl,status,RequestDate);

        this.sendLogs(logs);
    }

    private void sendLogs(RequestLog logs)
    {
        final String LogServiceUrl = "";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(logs);

        System.out.println(json);
        /*
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(LogServiceUrl);
        postMethod.setEntity(requestEntity);

        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(postMethod))
        {
            System.out.println(response.getStatusLine());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
         */
    }
}
