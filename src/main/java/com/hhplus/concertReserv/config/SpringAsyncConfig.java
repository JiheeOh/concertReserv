package com.hhplus.concertReserv.config;

import com.hhplus.concertReserv.interfaces.presentation.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 기본적으로 유지할 스레드 수
        threadPoolTaskExecutor.setCorePoolSize(5);
        // 최대 허용 스레드 수
        threadPoolTaskExecutor.setMaxPoolSize(10);
        // 작업 큐의 크기 (대기 중인 작업 수)
        threadPoolTaskExecutor.setQueueCapacity(500);
        // 스레드가 유휴 상태일 때 최대 유지 시간 ( 기본 60초 )
        threadPoolTaskExecutor.setKeepAliveSeconds(60);

        // 현재 실행중인 로직이 완료될때까지 기다렸다가 서비스를 종료(graceful shutdown)을 위한 옵션
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(10);

        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
