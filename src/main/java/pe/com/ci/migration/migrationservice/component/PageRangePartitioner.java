package pe.com.ci.migration.migrationservice.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class PageRangePartitioner implements Partitioner {

    private final int totalPages;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        int minPageNumber = 1;
        int maxPageNumber = totalPages;
        int pagesByPartition = (maxPageNumber - minPageNumber) / gridSize + 1;

        log.info("PageRangePartitioner - Paginas por Particion: {}", pagesByPartition);

        int numberOfPartition = 0;
        int startPageNumber = minPageNumber;
        int endPageNumber = minPageNumber + pagesByPartition - 1;

        while (startPageNumber <= maxPageNumber) {
            if (endPageNumber > maxPageNumber) {
                endPageNumber = maxPageNumber;
            }

            ExecutionContext executionContext = new ExecutionContext();
            executionContext.putInt("startPageNumber", startPageNumber);
            executionContext.putInt("endPageNumber", endPageNumber);

            result.put("partition" + numberOfPartition, executionContext);

            startPageNumber += pagesByPartition;
            endPageNumber += pagesByPartition;
            numberOfPartition++;
        }
        log.info("PageRangePartitioner - Particiones: {}", result.toString());
        return result;
    }
}