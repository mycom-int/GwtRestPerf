package org.restgwtperftest.client.cases;

public interface Case {
	String getName();
	void findBigBeans(int threadAmount, int beanAmount, int schPeriod);
	
	void doLongOperation(int threadAmount, int sleep , int schPeriod);
}
