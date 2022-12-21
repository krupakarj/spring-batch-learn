package com.example.demo.jobs;

import com.example.demo.*;
import com.example.demo.listeners.*;
import com.example.demo.policy.*;
import com.example.demo.service.*;
import com.example.demo.validator.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.*;
import org.springframework.batch.core.listener.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.*;
import org.springframework.batch.repeat.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.task.*;

import java.util.*;
import java.util.concurrent.*;

@EnableBatchProcessing
@ComponentScan(basePackages = "com.example.demo")
public class DemoApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DemoCompletionPolicy policy;

	@Value("${myurl}")
	private String myurl;

	@Autowired
	TestComponet tc;

	@Autowired
	private RunParameterValidator pval;

		/*@Bean
		public JobParametersValidator validator() {
			DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
			validator.setRequiredKeys(new String[] {});
			validator.setOptionalKeys(new String[] {"name","currentDate"});
			validator.afterPropertiesSet();
			return validator;
		}*/

	@Bean
	public Step step() {
		return this.stepBuilderFactory.get("step")
				.tasklet(new HelloWorldTasklet())
				.listener(promotionListener())
				.build();
				/*.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution,
												ChunkContext chunkContext) {
						System.out.println("Hello, World!");
						return RepeatStatus.FINISHED;
					}
				}).build();*/
	}

	@Bean
	public Step step2() {
		return this.stepBuilderFactory.get("step2")
				.tasklet(new GoodByeTasklet())
				.build();
	}
	@Bean
	public Step callableStep() {
			return this.stepBuilderFactory.get("callableStep")
					.tasklet(tasklet())
					.build();
	}

	@Bean
	public Step methodInvokingStep() {
			return this.stepBuilderFactory.get("methodInvokingStep")
					.tasklet(methodInvokingTasklet())
					.build();
	}

	@Bean
	public Step methodInvokingParamsStep() {
			return this.stepBuilderFactory.get("methodInvokingParamsStep")
					.tasklet(methodInvokingParamsTasklet(null))
					.build();
	}

	@Bean
	public Step chunkStep() {
			return this.stepBuilderFactory.get("chunkStep")
					.<String, String>chunk(randomCompletionPolicy())
					.reader(itemReader())
					.writer(itemWriter())
					//.taskExecutor(new SimpleAsyncTaskExecutor())
					.build();
	}
	//@Bean
	public StepExecutionListener promotionListener() {
		System.out.println("In promotion listener...");
		ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
		listener.setKeys(new String[] {"user.name"});
		try {
			listener.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return listener;

	}
	@Bean
	public Job job() {
		System.out.println("Using my URL: "+ myurl);
		return this.jobBuilderFactory.get("job")
				//.start(callableStep())
				.validator(pval.validator())
				.incrementer(new DailyJobTimestamper())
				//.next(step())
				//.next(step2())

				//.next(methodInvokingStep())
				//.next(methodInvokingParamsStep())
				.start(chunkStep())
				.listener(JobListenerFactoryBean.getListener(new JobLoggerListener()))
				.build();
	}

	//Callabla Tasklet Adapter

	@Bean
	public CallableTaskletAdapter tasklet() {
		CallableTaskletAdapter callableTaskletAdapter = new CallableTaskletAdapter();
		callableTaskletAdapter.setCallable(callableObject());
		return callableTaskletAdapter;
	}

	private Callable<RepeatStatus> callableObject() {
			return () -> {
				System.out.println("This was called in another thread.");
				return RepeatStatus.FINISHED;
			};
	}

	// method invocation tasklet adapter

	@Bean
	MethodInvokingTaskletAdapter methodInvokingTasklet() {
			MethodInvokingTaskletAdapter methodInvokingTaskletAdapter =
					new MethodInvokingTaskletAdapter();
			methodInvokingTaskletAdapter.setTargetObject(service());
			methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");

			return methodInvokingTaskletAdapter;

	}

	@StepScope
	@Bean
	MethodInvokingTaskletAdapter methodInvokingParamsTasklet(@Value("#{jobParameters['name']}") String message) {
		MethodInvokingTaskletAdapter methodInvokingTaskletAdapter =
				new MethodInvokingTaskletAdapter();
		methodInvokingTaskletAdapter.setTargetObject(service());
		methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");
		methodInvokingTaskletAdapter.setArguments(new String[] {message});

		return methodInvokingTaskletAdapter;

	}

	@Bean
	public CustomService service() {
			return new CustomService();
	}

	@Bean
	public ListItemReader<String> itemReader() {
		List<String> items =  new ArrayList<>(100);
		for(int i=0; i<100; i++) {
			items.add(UUID.randomUUID().toString());
		}
		return new ListItemReader<>(items);
	}

	@Bean
	public ItemWriter<String> itemWriter() {
			return items -> {
				for(String item: items) {
					System.out.println(">> Current Item: "+ item);
				}
			};
	}
	@Bean
	public CompletionPolicy randomCompletionPolicy() {
			return new RandomChunkSizePolicy();
	}


}
