package tsp;
/**   
* @Title: MutationOperator.java 
* @Package  
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-19
* @version V1.0   
*/

/**
* <p> MutationOperator</p>
* <p>�ṩ��һ��������쵽��һ������ķ����Ľӿ� </p>
* @author FlyingFish
* @date 2016-11-19
*/
public interface MutationOperator {
	
	/** 
	* <p>������� </p> 
	* <p>ע��Դ�����Ŀ�����Ӧռ�����β�ͬ���ڴ�ռ䡣</p> 
	* @param srcIndividual Դ���壨����ǰ��
	* @param dstIndividual Ŀ����壨�����
	*/
	public void operateMutation(Individual srcIndividual,Individual dstIndividual);
	
}
