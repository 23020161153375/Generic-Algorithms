package tsp;

import java.util.ArrayList;
import java.util.Random;

/**   
* @Title: OXOperator.java 
* @Package  
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-19
* @version V1.0   
*/

/**
* <p> OXOperator</p>
* <p>Description: </p>
* @author FlyingFish
* @date 2016-11-19
*/
public abstract class OXOperator {

	protected double rateOfOX;
	
	//debug
	private static Random random = new Random(120);
	
	protected OXOperator(double rate){
		rateOfOX = rate;
	}
	/** 
	* <p>����Ⱥ��ѡ�����������ӽ��� </p> 
	* <p>�ӽ����ӽ����ʵ�Ӱ�죬���������û�з����ӽ���������false��������ӽ�������true�� </p> 
	* @param population ��Ⱥ�����и���
	* @param parent ѡ����˫��
	* @param dst1 �Ӵ�����1����������ɳ�ʼ��
	* @param dst2 �Ӵ�����2����������ɳ�ʼ��
	* @return ֻ������ӽ��ŷ���true������Ϊfalse
	*/
	public abstract boolean operateOX(Individual[] population,Parent parent,Individual dst1,Individual dst2);
	
	
	
	public static void eliteSelect(Population srcPopul,Individual[] dstPopul,int dstIndex){
		ArrayList<Integer> eliteChrom =  srcPopul.getIndividual(srcPopul.getElite()).chromosome;
		double eliteFitness = srcPopul.getIndividual(srcPopul.getElite()).fitness;
		dstPopul[dstIndex].chromosome.clear();
		for(int i = 0; i < eliteChrom.size();i ++)
			dstPopul[dstIndex].chromosome.add(eliteChrom.get(i));	
		dstPopul[dstIndex].fitness = eliteFitness;
	}
	
	/** 
	* <p>����ѡ��֮����ѡ�� </p> 
	* <p>����ݸ�����Ӧ������Ⱥ��ѡ��������ͬ���壬�Ա���������ӽ����졣ע�⵽�����õ��ı��������ź����Ӧ�ȡ� </p> 
	* @param population ��Ⱥ
	* @param sumFitness ��Ⱥ��Ӧ��֮��
	* @return ������ѡ�еĸ�������Ⱥ�еı�ţ���0��ʼ��
	* @throws IllegalArgumentException ��Ⱥ�Ѿ���������������ͬ�ĸ���
	*/
	public  static Parent rouletteWheelSelect(Individual[] population,double sumFitness){
		if(sumFitness == 0.0)
			throw new IllegalArgumentException("��Ⱥ���и��嶼��һ���ģ�");
			
		int i = 0;
		double sumP = population[i].fitness / sumFitness; 
		//double r = Math.random();
		double r = random.nextDouble();
		while(sumP < r){
			i++;
			//System.out.println(i + " " + population.length);
			sumP = sumP + population[i].fitness / sumFitness;
		}
		
		//����һ����ͬ�ĸ���
		//ע����ʹ�ʽ�ı仯
		int j = i;
		int deadlockTag = 0;
		while(j == i){
			if(deadlockTag >= 100)
				throw new IllegalArgumentException("��Ⱥ�Ѳ�����������ͬ�ĸ��壨ע����ݱ���ѡ����Ӧ��Ϊ0�ĸ��岻�ڿ��Ƿ�Χ�ڣ�");				
			j = 0;
			 sumP = population[j].fitness / sumFitness; 
			 r = Math.random();
			 r = random.nextDouble();
			while(sumP < r){				
				j++;
				sumP = sumP + population[j].fitness / sumFitness;
			}
			deadlockTag ++;	
		}
		return new Parent(i,j);
	}
	

	
	
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param args 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���Ծ�Ӣѡ��
		Individual i1 = new Individual(1.0);
		Individual i2 = new Individual(4.5);
		Individual i3 = new Individual(4.5);
		Individual[] p1 = {i1,i2,i3};
		System.out.println("P1:{ " + p1[0].fitness + "," + p1[1].fitness + "," + p1[2].fitness+ " }");		
		/*
		Individual i4 = new Individual(5.5);		
		Individual i5 = new Individual(0.5);
		Individual[] p2 = {i4,i5};
		
		System.out.println("ǰ��P2:{ " + p2[0].fitness + "," + p2[1].fitness + " }");
		eliteSelect(p1,p2);
		System.out.println("��P2:{ " + p2[0].fitness + "," + p2[1].fitness + " }");
		*/
		//���Ա���ѡ��
		double sumOfFitness = 10;
		Parent parent;
		int c1 = 0,c2 = 0,c3 = 0;
		for(int i = 0; i < 100 ;i ++){
			parent = OXOperator.rouletteWheelSelect(p1, sumOfFitness);
			int k = parent.k1;
			switch(k){
			case 0:c1++;	break;
			case 1: c2++;break;
			case 2: c3++;break;			
			}
			k = parent.k2;
			switch(k){
			case 0:c1++;	break;
			case 1: c2++;break;
			case 2: c3++;break;			
			}			
		}//�������屻ѡ�еĸ��������ǣ�0.18��0.495��0.495
		System.out.println("100 * 2 = " +c1+" + " +c2+" + " +c3);;
	}

	
}
