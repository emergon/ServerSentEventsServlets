package com.emergon.sse;

import com.emergon.entity.Stock;
import com.emergon.entity.StockMarket;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StockEvent")
public class StockServlet extends HttpServlet {

    StockMarket stockMarket = new StockMarket();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        Stock stock = null;
        String eventType = null;
        while (true) {
            stock = stockMarket.getStock(req.getParameter("name"));
            eventType = req.getParameter("type");
            if (stock != null) {
                try {
                    writer = resp.getWriter();
                    if (eventType.equals("Price")) {
                        writer.print("event:Price\n");
                        writer.print("data: " + "Time:" + LocalTime.now() + "\n");
                        writer.print("data: " + "Name:" + stock.getName() + "\n");
                        writer.print("data: " + "Price:" + stock.getPrice() + "\n\n");

                    } else {
                        writer.print("event:Volume\n");
                        writer.print("data: " + "Time:" + LocalTime.now() + "\n");
                        writer.print("data: " + "Name:" + stock.getName() + "\n");
                        writer.print("data: " + "Volume:" + stock.getVolume() + "\n\n");
                    }
                    resp.flushBuffer();
                    Thread.sleep(4000);
                } catch (IOException | InterruptedException ex) {
                    writer.close();
                    break;
                }
            }
        }
    }

}
