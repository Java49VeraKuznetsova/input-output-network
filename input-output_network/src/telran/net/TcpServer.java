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
	/*
	private int cores;
	private ExecutorService threadPool;
	private BlockingQueue<TcpClientServer> queue;
	*/
	private int cores = Runtime.getRuntime().availableProcessors();
	private ExecutorService threadPool = Executors.newFixedThreadPool(cores);
	private BlockingQueue<TcpClientServer> queue = new LinkedBlockingQueue<>();
	
	public TcpServer(int port, ApplProtocol protocol) throws IOException {
		this.port = port;
		this.protocol = protocol;
		serverSocket = new ServerSocket(port);
		/*
		cores = Runtime.getRuntime().availableProcessors();
		ExecutorService threadPool = Executors.newFixedThreadPool(cores);
		BlockingQueue<TcpClientServer> queue = new LinkedBlockingQueue<>();
		*/
	}

	@Override
	public void run() {
		System.out.println("Server is listening on port " + port);
		try {
			while(true) {
				Socket socket = serverSocket.accept();
				TcpClientServer clientServer = new TcpClientServer(socket, protocol);
			    queue.put(clientServer);
				try {
					TcpClientServer clientServerCurrent = queue.take();
					threadPool.execute(clientServerCurrent);
					
				} catch (InterruptedException e){
					TcpClientServer clientServerReminder = null;
					while ((clientServerReminder = queue.poll()) != null) {
						threadPool.execute(clientServerReminder);
					}
					break;
				}
				
			
				//Thread thread = new Thread (clientServer);
				//thread.start();
				//clientServer.run();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}