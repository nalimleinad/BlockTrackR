package com.Geisteskranken.BlockTrackR;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class BlockTrackRExecutorService {

	final static ThreadFactory threadFactory = new ThreadFactoryBuilder()
			.setNameFormat("Orders-%d").setDaemon(true).build();

	final static ExecutorService ThreadPool = Executors.newFixedThreadPool(4,
			threadFactory);

	public void ThreadPoolStatus() {
		//BlockTrackR.logger.info();
		//Fetch ThreadPool repsonse time.
	}

}
