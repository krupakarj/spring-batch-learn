package com.example.demo.policy;

import org.springframework.batch.repeat.*;

import java.util.*;

public class RandomChunkSizePolicy implements CompletionPolicy  {
    private int chunkSize;
    private int totalProcessed;
    private Random random = new Random();

    @Override
    public boolean isComplete(RepeatContext repeatContext, RepeatStatus repeatStatus) {
        if(RepeatStatus.FINISHED == repeatStatus) {
            System.out.println("Based on Status, Chunk completed!!!!!!!!");
            return true;
        } else {
            return isComplete(repeatContext);
        }
    }

    @Override
    public boolean isComplete(RepeatContext repeatContext) {
        return this.totalProcessed >= chunkSize;
    }

    @Override
    public RepeatContext start(RepeatContext repeatContext) {
        this.chunkSize=random.nextInt(10);
        this.totalProcessed = 0;
        System.out.println("The Chunk Size has been set to: "+this.chunkSize);
        return repeatContext;
    }

    @Override
    public void update(RepeatContext repeatContext) {
        this.totalProcessed++;
    }
}
