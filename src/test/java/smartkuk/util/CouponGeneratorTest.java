package smartkuk.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CouponGeneratorTest {

  private static String taskName = CouponGeneratorTest.class.getName();
  StopWatch watch = new StopWatch(taskName);

  @Test
  public void generateTest() {

    // 생성 데이터 백만개
    int requestCount = 1000000;

    watch.start(taskName);
    List<char[]> results = CouponGenerator.generate(requestCount);
    watch.stop();

    log.info(watch.prettyPrint());

    assertNotNull(results);
    assertFalse(results.isEmpty());

    // 해쉬셋에 넣어서 갯수가 줄어드는지 검사
    HashSet<String> mergedSet = results.stream().map(raw -> {
      return new String(raw);
    }).collect(Collectors.toCollection(HashSet::new));

    assertTrue(requestCount == mergedSet.size());

  }
}
