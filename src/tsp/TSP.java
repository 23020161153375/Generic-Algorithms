package tsp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import dataStructSupport.SimplerandomSampling;

/**   
* @Title: TSP.java 
* @Package  
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-19
* @version V1.0   
*/

/**
* <p> ���Ŵ��㷨������������⡣</p>
* <p>�����ͨ���̳б������Ա��Ŵ��㷨���иĽ�����ʹ���Լ����Ŵ����㷨��Ϊ��ʵ�����Ŀ�ģ������¼����ֶΣ�</br>1.�ṩ�Լ��Ľ������� <code>OXOperator</code>��</br>2. �ṩ�Լ��ı������� <code>MutationOperator</code></br>3.�ṩ��ʼ��Ⱥ<br>4.����{@link #evolution()} �����¹滮��������.</p>
*<p>��������õ���Դ���£�</br>1.һ�����㽻������ <code>OnePointOXOperator</code></br>2.�������ʽ������ʼ��Ⱥ�ķ���{@link #initiatePopulation(long)}</br>3.�ֳɵĽ������{@link #evolution()}</p>
* @author FlyingFish
* @date 2016-11-19
*/
public class TSP {
	
	//������Ϣ
	protected CityPoint[] cities;
	
	/** 
	* @Fields start : TODO(��������ţ���1��ʼ) 
	*/ 
	protected int start;
	
	/**���о������*/
	protected double[][] distances;
	
	/**��Ⱥ��ģ*/
	protected int populationScale;	

	/**������Ⱥ��ÿ�ζ����������µ���Ⱥ*/
	protected Population oldPopulation ;
	
	/**�Ӵ���Ⱥ*/
	protected Population newPopulation ;
	
	/**�ܴ���*/
	protected int totalGenerations;	
	
	/**��ǰ��������ʼΪ1*/
	protected int currentGen = 1;
	
	/**�ӽ�����*/
	protected OXOperator operOX;
	
	/**��������*/
	protected MutationOperator operMutate;
	
	//���ڳ�ʼ����Ⱥ
	private SimplerandomSampling<Integer> rsampling;
	
	//����������Ҫ������ĳ���
	private Random random = new Random();
	

	public TSP(CityPoint[] cities,int start,int populScale,int totalGens,
			double rateOfOX,double pm1,double pm2){
		this.cities  = cities;
		this.distances = Util.Distance(cities);
		this.start = start ;
		
		this.populationScale = populScale;
		
		//��ǰ��newPopulatio����ռ�
		this.newPopulation = new Population(new Individual[populationScale]);		
		//һ���Ե���������Ⱥ��ģ�������ٺ�ÿ������Ŀռ�
		for(int i = 0; i < populationScale;i ++)
			newPopulation.setIndividual(i, new Individual(new ArrayList<Integer>(populationScale)));

		
		this.totalGenerations = totalGens;
		this.currentGen = 1;
		
		operOX = new OnePointOXOperator(rateOfOX);

		//ע���������ʹ��newPopulation��Ϊ��ϢԴ��
		//��Ϊ����������Ҫ�ȵ�newPopulation�ֺÿռ���ٶ�λ��newPopulation�ϡ�
		//ʵ���ϱ����캯������ͨ��ֱ�ӵ�������Ĺ��캯������ɡ�
		operMutate = new ReverseMutationOperator(pm1,pm2,newPopulation);			
	}	
	
	protected TSP(CityPoint[] cities,int start,int populScale,int totalGens,
			OXOperator operOX,MutationOperator operMutate){
		this.cities  = cities;
		this.distances = Util.Distance(cities);
		this.start = start ;
		
		this.populationScale = populScale;					
		this.totalGenerations = totalGens;
		this.currentGen = 1;
		
		this.newPopulation = new Population(new Individual[populationScale]);		
		//һ���Ե���������Ⱥ��ģ�������ٺ�ÿ������Ŀռ�
		for(int i = 0; i < populationScale;i ++)
			newPopulation.setIndividual(i, new Individual(new ArrayList<Integer>(populationScale)));

		
		this.operOX = operOX;
		this.operMutate = operMutate;
	}
	
	/** 
	* <p>������ķ�ʽ������ʼ��Ⱥ</p> 
	* <p>Description: </p> 
	* @param seed ���������
	*/
	protected void initiatePopulation(long seed){

		Integer[] tags = new Integer[cities.length - 1];
		int tagsIndex = 0;
		//���д�1��ʼ��ţ����������������г���
		//��Ϊһ�����壬���м����������ÿ����
		//���������еĸ��嶼����ȡ�����Ϳɵõ�һ�������Ľ�
		for(int i = 1;i <= cities.length;i++)
			if(i!= start)//���������������г��б�����η���tags
				tags[tagsIndex ++] = i;
		
		//׼��������һ����Ⱥ
		rsampling = new SimplerandomSampling<Integer>(tags,seed);		
		random = new Random(seed);
		
		this.oldPopulation= new Population(new Individual[populationScale]);
		int[] tempChrom = new int[cities.length];
		
		for(int i = 0;i < populationScale;i++){
			
			//�õ�һ��Ⱦɫ��/·��
			tempChrom[0] = start;
			for(int j = 1;j < cities.length;j ++)
				//ע������Ѿ�ѡ�����ڵ�һλ�ģ����Բ��þ�������
				tempChrom[j] = rsampling.sample();
			
			//��·�������¸���
			oldPopulation.setIndividual(i, new Individual(tempChrom)); 
			
			//����
			rsampling.reset();			
		}
		
		//��ʽ������һ����Ⱥ
		this.populationSetup(oldPopulation);
		
		
		
		/*
		Integer padding = 0;ArrayList<Integer> receiver = new ArrayList<Integer>();
		for(int i = 0; i < populationScale;i ++){
			//��Ϊ�����㷨��Ҫ��ǿ�и�Ⱦɫ���ϵĻ���ռλ
			//��������������chromosome.size()��ֵ
			receiver = newPopulation.getIndividual(i).chromosome;
			for(int j = 0; j < cities.length;j ++)
				receiver.add(padding);
		}
		*/
	} 	
	
	/**��Ϊ��ֹ֮һ�����Ÿ������Ӧ��������С�ڵ�������ֵ����ֹ����*/
	public static double LIMIT_AUG_RATE = 0.0001;
	
	public void solveTSP(){
		this.initiatePopulation(320);
		double  record = oldPopulation.getIndividual(oldPopulation.getElite()).fitness;
		double  augRate = 1;
		do{
			try{
				evolution();
				augRate = (oldPopulation.getIndividual(oldPopulation.getElite()).fitness - record) / record;
				record = oldPopulation.getIndividual(oldPopulation.getElite()).fitness;
			}catch( IllegalArgumentException e){			
				System.out.println(e.getMessage()+"\n�����жϣ�");
				break;
			}
			//�����Ÿ������Ӧ��������С�ڵ�������ֵʱ�����ߵ�ǰ�����Ѿ��ﵽ�ܴ���ʱ����ֹ
			//��ʹ���˾�Ӣѡ��������ΪʲôaugRate��Ϊ��ֵ��
			//��Ϊ����ʹ�õ��Ǿ����ŵ���Ե���Ӧ�ȣ������Ǿ��Ե�·�����ۡ�
		}while(Math.abs(augRate) > LIMIT_AUG_RATE && currentGen <= totalGenerations);

		Individual optPath = oldPopulation.getIndividual(oldPopulation.getElite());
		System.out.println("\n\n*************���*************\n��С·��������" + this.calculateCost(optPath.chromosome));
		System.out.println("����·��Ϊ��\n" + optPath.chromosome);
		System.out.println("��ֹ������" + currentGen );
		System.out.println("���յ���Ⱥ��");
		oldPopulation.showInfo();	
	}
	
	
	/** 
	* <p>���� </p> 
	* <p>���ԣ���ͨ������ѡ�񡢵��㽻�����ͬ�ȹ�ģ����Ⱥ1���پ�������õ���Ⱥ2��������þ�Ӣѡ����Դ��Ⱥ�ľ�Ӣ�����滻����Ⱥ2���������塣�õ����յ�����Ⱥ�� </p> 
	* @return 
	*/
	public void evolution(){

		System.out.println("������"+currentGen);
		oldPopulation.showInfo();
	
		//1.����ѡ�񡢵��㽻��
		Parent parent = 
				OXOperator.rouletteWheelSelect(oldPopulation.getPopulation(), oldPopulation.getSumOfFitness());
		Individual subInd1,subInd2;

		boolean successfullyOX = false;
		
		int deadlockIndex = 0;
		for(int subGenIndex = 0;subGenIndex < populationScale;deadlockIndex++){
			if(deadlockIndex >= 100)
				throw new IllegalArgumentException("�޷������µ���Ⱥ");

			subInd1 = newPopulation.getIndividual(subGenIndex);

			subInd2 = newPopulation.getIndividual(subGenIndex+1);

			successfullyOX =operOX.operateOX(oldPopulation.getPopulation(), parent, subInd1, subInd2);	

			if(successfullyOX){
				subGenIndex+= 2;
			}
			parent = OXOperator.rouletteWheelSelect(oldPopulation.getPopulation(), oldPopulation.getSumOfFitness());
		}
			
		//��Ӣѡ��,���ڹ�ģΪ��������Ⱥ���������������Ӵ�
		OXOperator.eliteSelect(oldPopulation, newPopulation.getPopulation(), populationScale - 1);
			
		//����֮ǰ��һЩ���ã���Ӧ�Ⱥ���Ⱥ��Ϣ��		
		populationSetup(newPopulation);

		//����
		for(int i = 0; i < populationScale;i ++)
			operMutate.operateMutation(newPopulation.getIndividual(i), oldPopulation.getIndividual(i));
	
		//���úñ�������Ⱥ
		populationSetup(oldPopulation);		


	
		//��Ⱥ������1
		 ++currentGen;
	}
	
	
	public TSP(CityPoint[] cities,int start,int populScale,int totalGens){
		this(cities,start,populScale,totalGens,0.99,02,0.02);
	}

	private  void populationSetup(Population popul){
		int populationScale = popul.getPopulationScale();
		for(int i = 0; i < populationScale;i ++)
			popul.getIndividual(i).setFitness(
					calculateCost(
							popul.getIndividual(i).chromosome));
		gama(popul.getPopulation());
		popul.statisticsIndivInfo();
		popul.staticsticsWholeInfo();
	}
	
	/** 
	* <p>gamaΪ��Ӧ�����ź��� </p> 
	* <p> fitness(i,t) = Max(tempFitness(j,t)) - tempFitness(i,t), i,j��[1..scale]</br>
		Max(tempFitness(j,t))��ʾtʱ�̣���������Ⱥ����δ���ŵ���Ӧ�ȣ������о�������ֵ</p> 
	* @param popul ��Ⱥ
	*/
	private static void gama(Individual[] popul){
		int populationScale = popul.length;
		
		double tempMaxFitness = 0;
		for(int i = 0;i < populationScale;i++)		
			if(popul[i].fitness > tempMaxFitness)
				tempMaxFitness = popul[i].fitness;
				
		for(int i = 0; i < populationScale;i ++)
			popul[i].setFitness(tempMaxFitness - popul[i].fitness  );
	}
	
	/** 
	* <p>����·������</p> 
	* @param solution 
	*@return double 
	*/
	private  double calculateCost(ArrayList<Integer> solution){
		double cost = 0 ;
		for(int i = 0;i < solution.size() - 1; i++)
			cost += distances[solution.get(i)-1][solution.get(i + 1) - 1];
		
		//�����ϴ��յ�վ�ص����ĳ���
		cost += distances[solution.get(solution.size() - 1) - 1][start - 1];
		
		return cost;
	}
	
	/**
	* <p> ʹ�õ��ñ��취�ı������ӡ�������̳�TSP���Ӵ�ʹ�ã������Ѿ�������Ľ������̷ǳ���Ϥ��</p>
	* <p>��������һ�����ʵġ���һ����Ⱥ������õ���Ӧ�ȸ��������ʸߣ�����Ӧ�ȸ���������С���ֱ��ΪPm1��Pm2����һ���棬�����ó�ʼʱ�ı�����ʴ�һЩ���Ա�������������Ŀռ䣬�����ݻ����̽ӽ�β�����������Pm1��Pm2�𽥱�Ϊ0���ý�������� </p>
	* @author FlyingFish
	* @date 2016-11-20
	*/
	private class ReverseMutationOperator implements MutationOperator{
		private double lowerPm,upperPm;
		private Population srcPopul;
		
		/** 
		* <p>Title: </p> 
		* <p>Description: </p> 
		* @param upperPm
		* @param lowerPm
		* @param srcPopul ʹ�ñ������ʱ��Դ����һ��Ҫ�������Դ��Ⱥ
		*/
		public ReverseMutationOperator(double upperPm,double lowerPm,Population srcPopul){
			this.lowerPm = lowerPm;
			this.upperPm = upperPm;
			this.srcPopul= srcPopul;
		}
				
		/** ʹ�õ��ñ��취��
		 * <p>���ѡȡ�����㣨��������㣩����������֮��İ������������ڵ�·�����á�������ģ���˻��еĶ��任������������ĸ�����Ӧ�ȸ��ã����滻ԭ���壻���򣬱��ֲ��䡣</p>
		 * <p>������һ�����ʣ����û�з������죬Ŀ����彫ֱ����Դ����Ŀ�����</p>
		 * @see tsp.MutationOperator#operateMutation(tsp.Individual, tsp.Individual)
		 * @throws IllegalArgumentException ��������������Ƕ�ͬһ�����������
		 */
		@Override
		public void operateMutation(Individual srcIndividual, Individual dstIndividual) {
			// TODO Auto-generated method stub
			if(!checkAddress(srcIndividual,dstIndividual))
				throw new IllegalArgumentException("Դ������Ŀ����岻�ܹ���ͬһ�οռ�");
			
			double pm = calcIndividualPm(srcIndividual);
			
			boolean mutated = false;
			ArrayList<Integer> newChrom = dstIndividual.chromosome;
			if(random.nextDouble() < pm){
					
				//newChrom�����ǲ����
				newChrom.clear();
				
				//ע�����ʼ���ǲ����
				int l = (int)(random.nextDouble()* (srcIndividual.chromosome.size() - 1)) + 1;
				int r = (int)(random.nextDouble()* (srcIndividual.chromosome.size() - 1)) + 1;
				
				if(l > r){//����l��r������
					int temp = l;
					l = r;
					r = temp;
				}
				//System.out.println("l , r:" + l +  "  " + r);
				
				for(int i = 0; i < l;i ++)
					newChrom.add(srcIndividual.chromosome.get(i));
				for(int i = r;i >= l;i --)
					newChrom.add(srcIndividual.chromosome.get(i));
				for(int i = r+1; i < srcIndividual.chromosome.size();i ++)
					newChrom.add(srcIndividual.chromosome.get(i));	
				
				//�Ƚϱ���ǰ����������Ӧ�ȡ�
				if(calculateCost(srcIndividual.chromosome) > calculateCost(newChrom))
					mutated = true;
			}
			if(!mutated){//�������û�гɹ�,����ԭ���ĸ���
				for(int i = 0;i < srcIndividual.chromosome.size();i ++)
					newChrom.set(i,srcIndividual.chromosome.get(i));
			}				
		}
		
		private boolean checkAddress(Individual src,Individual dst){
			return src != dst;
		}
		
		/** 
		* <p>���㵱ǰ����ı������</p> 
		* <p>�鿴{@link ReverseMutationOperator}����ر�����ԡ�ʹ�ù�ʽ 
		* </br> Pm(t)= Pm1(t) +(Pm2(t)-Pm1(t))*(F-Favg)/(Fmax - Favg) ,(F>Favg)</br>Pm(t) = Pm1(t),(F <= Favg) </br>������Pm������Pm1(t)��Pm2(t)��ʾtʱ�̵ı������ϡ��½磬�ǳ�ʼֵ����˥�����ӵĽ����˥������factor���㹫ʽΪ</br>factor =1.0 - 1.0 * currentGen / totalGenerations</p> 
		* @param srcPopulation
		* @return 
		*/
		private double calcIndividualPm(Individual srcIndiv){
			double factor = 1.0 - 1.0 * currentGen / totalGenerations;
			double pm1 = upperPm * factor,pm2 = lowerPm * factor;
			double pm ,deltaF = srcIndiv.fitness - srcPopul.getAverageOfFitness();
			if(deltaF > 0)
				pm = pm1 + (deltaF) 
					/ (srcPopul.getIndividual(srcPopul.getElite()).fitness 
							- srcIndiv.fitness)
					* (pm2-pm1);
			else
				pm = pm1;
			
			return pm;
		}	
	}

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param args 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TSP tspFormal = new TSP(Util.ReadFile("supplements/att48.tsp"),1,100,1000);
		tspFormal.solveTSP();		
	}
		
	
	/**
	* <p> �����ӽ�����</p>
	* <p>�ӽ����̡����ѡ��һ���ӽ��㣬���ӽ������ĵ�λ��������������Ȼ��������Ⱦɫ����ײ����ӽ����Ϸֱ��ҳ��ظ��ı��루���У��������� </p>
	* <p>ע���ӽ���λ����[0...lenOfChromosome - 2]�ķ�Χ�ϡ�</p>
	* @author FlyingFish
	* @date 2016-11-21
	*/
	protected class OnePointOXOperator extends OXOperator{
		
		private boolean[][] cityExist ;
			
		/** 
		* <p>Title: </p> 
		* <p>Description: </p> 
		* @param rate 
		*/
		protected OnePointOXOperator(double rate) {
			super(rate);
			// TODO Auto-generated constructor stub
			
			//cityExist[0]���ڼ���һ��ԴȾɫ��ǰ���뽻��������
			//�ڶ���ԴȾɫ������Ƿ��ظ�������У���һ�������Ⱦɫ�壩
			//cityExist[1]���
			cityExist = new boolean[2][cities.length];
		}

		private void init(){
			for(int i = 0; i < 2;i ++ )
				for(int j = 0;j < cities.length;j ++)
					cityExist[i][j] = false;
		}
		
		
		/** �������㽻���,��һ������õ���һ�������ǰ���Ⱦɫ�壨�����޸ģ��͵ڶ�������ĺ���Ⱦɫ�塣�ڶ���������ȡ�
		 * @see tsp.OXOperator#operateOX(tsp.Individual[], tsp.Parent, tsp.Individual, tsp.Individual)
		 *@throws IllegalArgumentException ��������������Ƕ�ͬһ�����������,������Ϊ����δ֪ԭ�����޷�����ӽ�
		 */
		@Override
		public boolean operateOX(Individual[] population, Parent parent, Individual dst1, Individual dst2) {
			// TODO Auto-generated method stub
			if(!this.checkAddress(population[parent.k1], population[parent.k2], dst1, dst2))
				throw new IllegalArgumentException("˫�����Ӵ����ܹ��ÿռ�");
				
			if(random.nextDouble() > rateOfOX)//���ܲ�������ĸ��� = 1 - rateOfOX
				return false;	
			
			//��ʼ��cityExist
			init();
			
			//�ӽ�ǰ׼��
			ArrayList<Integer> chrom1 ,chrom2;
			chrom1 = population[parent.k1].chromosome;
			chrom2 = population[parent.k2].chromosome;
			dst1.chromosome.clear();
			dst2.chromosome.clear();
			
			//ѡ���ӽ���
			int bound = chrom1.size() - 1,pointOfOX;
			pointOfOX  = (int)(Math.random() * bound);
			
			for(int i = 0; i < chrom1.size(); i ++){
				if(i > pointOfOX){
					//�ӽ����ĵ�λ���򻥻��������ñ��		
					dst1.chromosome.add(chrom2.get(i));
					cityExist[0][chrom2.get(i) - 1] = true;
					dst2.chromosome.add(chrom1.get(i));
					cityExist[1][chrom1.get(i) - 1] = true;
				}else{//�ӽ��㼰ǰ��λ���ϵĻ������Զ�Ӧ��������
					//���滹�����һ���ϵĻ�������޸�
					dst1.chromosome.add(chrom1.get(i));		
					dst2.chromosome.add(chrom2.get(i));	
				}
			}
			
			int deadlockIndex = 0;
			int src1 = 0,src2 = 0;
			while(src1 <= pointOfOX || src2 <= pointOfOX){
				if(deadlockIndex >= chrom1.size() + 1){//���׼������û���ã��������׳�������
					System.out.println("Դ����Ⱦɫ��\n" +chrom1+" \n" +chrom2);
					System.out.printf("pointOfOX,src1,src2%5d%5d%5d\n" ,pointOfOX,src1,src2 );
					throw new IllegalArgumentException("�޷�����ӽ�");
				}
				while(src1 <= pointOfOX &&!cityExist[0][chrom1.get(src1) - 1] ) {src1++;}
				while(src2 <= pointOfOX && !cityExist[1][chrom2.get(src2) - 1] ) {src2++;}
				if(src1 <= pointOfOX && src2 <= pointOfOX){
					//��Ȼ��0��pointOfOXλ����dst1��dst2���������ظ�Ԫ��һ�����
					//��ô����һ����ͬʱ��ɶ��ظ�Ԫ�صĴ�����Ϊÿ�����Ǹ��Ի�
					//��һ���ظ����ڽ��н��渳ֵ�����෴�����һ���Ѿ�����˶�����
					//Ԫ�صĴ��������ظ�Ԫ�أ�������Զ϶���һ����û�пɴ�����ظ�Ԫ�ء�
					dst1.chromosome.set(src1, chrom2.get(src2));
					dst2.chromosome.set(src2, chrom1.get(src1));
					src1++;
					src2++;
				}	
			}
			return true;
		}
		
		private boolean checkAddress(Individual p1,Individual p2,Individual s1,Individual s2){
			if(p1 == s1|| p1 == s2 || p2 == s1 || p2 == s2)
				return false;
			else
				return true;		
		}
		
	}

}
