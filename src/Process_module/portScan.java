package Process_module;

import java.util.ArrayList;

public class portScan {
	
	private Filter filter;
	private BF bf;
	private CBF cbf;
	ArrayList<String> packs;
	
	public portScan(ArrayList<String> packages) {
		
		filter = new Filter(1);
		cbf = new CBF();
		bf = new BF();
		packs = new ArrayList<String>();
		
		for(int i=0;i<packages.size();i++) {
			String subSentences[] = packages.get(i).split(",");
			packs.add(subSentences[0]+","+subSentences[1]+","+subSentences[5]);  //源地址宿地址和宿端口
		}
		process();
	}
	
	private void process() {
		for(int i=0;i<packs.size();i++) {
			String subSentences[] = packs.get(i).split(",");
			String t = subSentences[0]+","+subSentences[1];  //源地址宿地址
			if(filter.BloomJudge(t)) {
				cbf.Count(t);
				bf.BloomInsert(packs.get(i));
			}
		}
	}
	
	public void show() {
		cbf.Statistics(filter.Statistics, 1);
		bf.Statistics(filter.Statistics, 1);
		System.out.println("\r\n端口扫描结果");
		for(int i=0;i<filter.Statistics.size();i++) {
			String t = filter.Statistics.get(i);
			if(cbf.StatisticsCount.get(t) > 150)
				System.out.println( cbf.StatisticsCount.get(t)+ "  " + bf.StatisticsCount.get(t));
		}
	}
	
}
