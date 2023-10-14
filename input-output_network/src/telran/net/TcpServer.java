package telran.net;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
public class TcpServer implements Runnable {
	private int port;
	private ApplProtocol protocol;
	private ServerSocket serverSocket;
	
	private int cores = Runtime.getRuntime().availableProcessors();
	private ExecutorService threadPool = Executors.newFixedThreadPool(cores);
	
	
	public TcpServer(int port, ApplProtocol protocol) throws IOException {
		this.port = port;
		this.protocol = protocol;
		serverSocket = new ServerSocket(port);
	
	}

	@Override
	public void run() {
		System.out.println("Server is listening on port " + port);
		System.out.println("cores " + cores);
		while(true) {
		try {
			
				Socket socket = serverSocket.accept();
				TcpClientServer clientServer = new TcpClientServer(socket, protocol);
			 			threadPool.execute(clientServer);
					
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		}
}
	}
		