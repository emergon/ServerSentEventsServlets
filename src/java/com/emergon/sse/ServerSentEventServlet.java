package com.emergon.sse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SseServlet")
public class ServerSentEventServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        while(true){
            try{
                double randomNum = Math.random() * 10000;
                writer = resp.getWriter();
                writer.print("data: "+"Next event will be in "+Math.round(randomNum/1000)+" seconds.\n");
                writer.print("data: "+"Time:"+LocalTime.now()+"\n\n");
                resp.flushBuffer();
                Thread.sleep((long)randomNum);
            }catch(IOException | InterruptedException ex){
                writer.close();
                break;
            } 
            
            
            
        }
    }
    
}
