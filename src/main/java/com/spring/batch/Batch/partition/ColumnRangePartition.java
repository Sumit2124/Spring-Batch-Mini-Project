package com.spring.batch.Batch.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartition implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();

        // Logic to determine the min and max values for partitioning
        int minId = 1;// Implement this method
        int maxId = 1000; // Implement this method

        int range = (maxId - minId + 1) / gridSize; // Calculate the range for each partition

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            int start = minId + i * range;
            int end = (i == gridSize - 1) ? maxId : start + range - 1; // Last partition goes to maxId
            context.putInt("start", start);
            context.putInt("end", end);
            partitions.put("partition" + i, context);
        }

        return partitions;
    }
}
