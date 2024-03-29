package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bo.utils.BigDecimalUtils;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class DividendYield extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + DividendYield.class.getName());

	@Override
	protected BigDecimal process(ViewableAnalysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		BigDecimal divisor = analysis.getTotalCost().add(analysis.getTotalSold()).subtract(analysis.getCapitalGain());

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("dividend yield = dividend / divisor");
			calculsLog.info("divisor = (getCurrentCost + getTotalSold - getCapitalGain)");
			calculsLog.info("getCurrentCost: {}", new Object[] { analysis.getTotalCost() });
			calculsLog.info("getTotalSold: {}", new Object[] { analysis.getTotalSold() });
			calculsLog.info("getCapitalGain: {}", new Object[] { analysis.getCapitalGain() });
			calculsLog.info("getDividend: {}", new Object[] { analysis.getDividend() });
			calculsLog.info("divisor: {}", new Object[] { divisor });
		}

		// TODO use averageCostPerDay instead
		if (BigDecimalUtils.isPositive(divisor)) {
			value = analysis.getDividend().multiply(BigDecimalUtils.HUNDRED).divide(divisor, BigDecimal.ROUND_UP);
			calculsLog.info("Calculated dividend yield successfully");
		} else {
			calculsLog.info("Failed to calculate dividend yield: divisor is not over 0");
		}

		calculsLog.info("setDividendYield = " + value);
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setDividendYield(value);
	}
}
