package com.smartrouter.dto;

import java.util.*;

public class TrendData {
    private List<String> dates;
    private List<Long> tokenCounts;
    private List<Long> requestCounts;
    private List<Double> latencies;
    private List<Double> failRates;
    private List<Long> tier1Tokens;
    private List<Long> tier2Tokens;
    private List<Long> tier3Tokens;

    public TrendData() {}

    public List<String> getDates() { return dates; }
    public void setDates(List<String> v) { this.dates = v; }
    public List<Long> getTokenCounts() { return tokenCounts; }
    public void setTokenCounts(List<Long> v) { this.tokenCounts = v; }
    public List<Long> getRequestCounts() { return requestCounts; }
    public void setRequestCounts(List<Long> v) { this.requestCounts = v; }
    public List<Double> getLatencies() { return latencies; }
    public void setLatencies(List<Double> v) { this.latencies = v; }
    public List<Double> getFailRates() { return failRates; }
    public void setFailRates(List<Double> v) { this.failRates = v; }
    public List<Long> getTier1Tokens() { return tier1Tokens; }
    public void setTier1Tokens(List<Long> v) { this.tier1Tokens = v; }
    public List<Long> getTier2Tokens() { return tier2Tokens; }
    public void setTier2Tokens(List<Long> v) { this.tier2Tokens = v; }
    public List<Long> getTier3Tokens() { return tier3Tokens; }
    public void setTier3Tokens(List<Long> v) { this.tier3Tokens = v; }
}
