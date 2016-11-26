package dataStructSupport;

import java.util.ArrayList;
import java.util.Random;

/**   
* @Title: SimplerandomSampling.java 

* @Package  
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-19
* @version V1.0   
*/

/**
* <p> SimplerandomSampling</p>
* <p>ʵ�ּ�������� </p>
* @author FlyingFish
* @date 2016-11-19
*/
public class SimplerandomSampling<E> {

	private MyLinkedList<E> population;
	
	
	/** 
	* @Fields tagIndex : TODO(��ʶ�������и���Ĵ��ڷ�Χ</br>population[0...tagIndex]) 
	*/ 
	private int tagIndex = -1;
	
	private Random random;
	
	public SimplerandomSampling(E[] population){
		this(population,0);
	}
	
	public SimplerandomSampling(E[] population,long seed){
		this.population = new MyLinkedList<E>(population);
		tagIndex = this.population.size() - 1;
		random = new Random(seed);		
	}
	
	
	/** 
	* <p>�����������н���һ�μ����������</p> 
	* <p>����ǰӦ��֤���������и��塣��������{@link #isEmpty()}���жϡ�</p> 
	* @return ����
	*/
	public E sample(){
		//index ��0��tagIndex֮��
		int index = random.nextInt(tagIndex + 1);
		E elemTookOut = population.remove(index);
		population.addLast(elemTookOut);
		tagIndex --;
		return elemTookOut;
	}
	
	/** 
	* <p>������������������س�ȡm�������� </p> 
	* <p>ע�⣬����������������m����������ķ�ʽ��ʣ�����и���ȡ��������ͨ��{@link #amountOfIndividual()} ȷ�ϵ�ǰ�����еĸ�������</p> 
	* @param m Ҫ��ȡ��������Ŀ
	* @return �����ȡ��������
	*/
	public ArrayList<E> sample(int m){
		ArrayList<E> samples = new ArrayList<E>();
		
		while(m-- != 0 && !isEmpty()){
			samples.add(sample());
		}
		
		return samples;
	}
	
	public int amountOfIndividual(){
		if(tagIndex < 0)
			return 0;
		else
			return tagIndex + 1;
	}
	
	/**�������Ƿ���Ԫ�ؿ��Խ��г���*/
	public boolean isEmpty(){
		return tagIndex < 0;
	}
	
	/** 
	* <p>���ã����Խ�����ָ�������ǰ�ĳ�ʼ״̬ </p> 
	* <p>Description: </p>  
	*/
	public void reset(){
		tagIndex = population.size - 1;
	}
	
	/** 
	* <p>�����µ�α��������� </p> 
	* <p>Description: </p> 
	* @param seed 
	*/
	public void setSeed(long seed){
		random = new Random(seed);
	}
	
	
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param args 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����1
		Integer[] intSet = new Integer[10];
		for(int i = 0; i < intSet.length;i++)
			intSet[i] = i + 1;
		System.out.println("��1��10��ɵ������н��м��������\n��һ�֣�");
		SimplerandomSampling<Integer> srSampling = new SimplerandomSampling<Integer>(intSet); 
		for(int i = 0; i < 4;i ++)
			System.out.print(srSampling.sample() + " " );

		System.out.println("ʣ����壺" + srSampling.amountOfIndividual());
		ArrayList<Integer> samp = srSampling.sample(6);
		while(!samp.isEmpty())
			System.out.print(samp.remove(0) + " " );		
		System.out.println("\n����Ԫ�أ� " + !srSampling.isEmpty() );
		
		//����2
		System.out.println("�ڶ��֣�");
		srSampling.reset();
		samp = srSampling.sample(12);
		while(!samp.isEmpty())
			System.out.print(samp.remove(0) + " " );				
		System.out.println("\nʣ����� " + srSampling.amountOfIndividual() );
	}

}
