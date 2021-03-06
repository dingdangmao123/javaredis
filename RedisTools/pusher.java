package RedisTools;

import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel; 
import java.util.concurrent.*;

public class pusher implements Runnable{

	LinkedBlockingQueue<SelectionKey> client;
	LinkedBlockingQueue<Redis> command;

	public	pusher(LinkedBlockingQueue<SelectionKey> client,
		LinkedBlockingQueue<Redis> command){

		this.client=client;
		this.command=command;
	}

	public void run(){

		Redis tmp;
		ByteBuffer cb=ByteBuffer.allocate(128);
		System.out.println("pusher thread start......");
		String res;

		try{

			while(true){

				while((tmp=command.take())!=null){
					
					System.out.println(tmp);
					res=RedisRoute.parse(tmp.cli,tmp.cmd);
					res=res+"*";
					echo.write(tmp,res);
					cacheRedis.set(tmp);

				}

			}


		}catch(Exception e){  

			e.printStackTrace();


		}
	}


}