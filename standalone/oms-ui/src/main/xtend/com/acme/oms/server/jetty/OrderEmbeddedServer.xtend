package com.acme.oms.server.jetty

import bekkopen.jetty.ShutdownHandler
import com.acme.oms.ui.OrderWebServlet
import com.acme.oms.ui.OrderWebUI
import java.io.IOException
import java.net.URL
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.eclipse.jetty.webapp.WebAppContext

class OrderEmbeddedServer {
    val static DEFAULT_PORT         = 7080
    val static DEFAULT_CONTEXT_PATH = "/vaadin"
    val static DEFAULT_SECRET       ="eb27fb2e61ed603363461b3b4e37e0a0"

    val static MAX_THREADS          = 128
    val static WRONG_EXIT           = -1
    
    def static main(String[] args) throws Exception {
        val name = if (args.length < 1) Command.start.name() else args.get(0)
        getCommandFromName(name).run
    }

    def package static start() {
        try {
            val server = doCreateServer
            val context = doCreateContext(server)
            link(server, context)
            server.start()
            server.join()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
    
    def private static link(Server server, WebAppContext context) {
        val handlers = new HandlerList
        handlers.addHandler(context)
        handlers.addHandler(new ShutdownHandler(server, context, DEFAULT_SECRET))
        server.handler = handlers
    }

    def private static doCreateServer() {
        val threadPool = new QueuedThreadPool(MAX_THREADS)
        val server = new Server(threadPool)
        server.stopAtShutdown = true
        server.stopTimeout = 5000

        val connector = new ServerConnector(server)
        connector.port = DEFAULT_PORT
        connector.idleTimeout = 30000 
        server.connectors = #[connector]
        server
    }

    def private static doCreateContext(Server server) throws IOException {
        // Get the war-file
        val location = OrderEmbeddedServer.protectionDomain.codeSource.location
        val warFileUrl = cleanWrongUrl(location)

        // Add the warFile (this jar)
        val context = new WebAppContext(warFileUrl, DEFAULT_CONTEXT_PATH)
        context.server = server
        
        val vaadinLoader = new ServletHolder(new OrderWebServlet)
        vaadinLoader.setInitParameter("ui", OrderWebUI.name)
        vaadinLoader.setInitParameter("productionMode", OrderWebServlet.productionMode.toString)
        context.addServlet(vaadinLoader, OrderWebServlet.urlPatterns)  // OK as urlPatterns contains only one url
        return context;
    }
    
    // why this exclamation mark?
    def private static cleanWrongUrl(URL url) {
        url.toExternalForm.replaceFirst("(?<!\\.(?:jar|war))!/$", "/")
    }

    def package static stop() {
        println(ShutdownHandler.shutdown(DEFAULT_PORT, DEFAULT_SECRET))
    }

    def package static usage() {
        val sep = System.lineSeparator + "\t"
        val manual = new StringBuilder("Usage: java -jar <file.jar> [")
        val options = Command.values.map[name].reduce[r, name | r + (if (!r.empty) "|") + name]
        manual.append(options).append("]").append(sep)
        val instructions = Command.values.map[message].reduce[r, msg | r + (if (!r.empty) sep) + msg]
        manual.append(instructions).append(System.lineSeparator)
        println(manual)
    }
    
    def private static getCommandFromName(String name) {
        val command = Command.find(name)
        if (command === null) {
            Command.usage.run
            System.exit(WRONG_EXIT)
        }
        command
    }
    
}