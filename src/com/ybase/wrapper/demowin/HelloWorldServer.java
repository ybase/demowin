package com.ybase.wrapper.demowin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloWorldServer implements Runnable {

	private final Socket m_socket;
	private final int m_num;

	HelloWorldServer(Socket socket, int num) {
		m_socket = socket;
		m_num = num;

		Thread handler = new Thread(this, "handler-" + m_num);
		handler.start();
	}

	@Override
	public void run() {
		System.out.println(m_num + "Connected.");
		try {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						m_socket.getInputStream()));
				OutputStreamWriter out = new OutputStreamWriter(
						m_socket.getOutputStream());
				out.write("Welcome connection #" + m_num + "\r\n");
				out.flush();

				boolean flag = true;
				while (flag) {
					String line = in.readLine();
					if (line == null) {
						System.out.println(m_num + " Closing Connection.");
						flag = false;
					} else {
						System.out.println(m_num + " Write: echo" + line);
						out.write("echo" + line + "\n\r");
						out.flush();
					}
				}

			} finally {
				m_socket.close();
			}
		} catch (IOException e) {
			System.out.println(m_num + " Error: " + e.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 9000;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		System.out.println("Accepting connections on port: " + port);
		int nextNum = 1;
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket socket = serverSocket.accept();
			HelloWorldServer hw = new HelloWorldServer(socket, nextNum++);
		}
	}

}
