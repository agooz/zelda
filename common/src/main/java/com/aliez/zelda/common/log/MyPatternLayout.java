package com.aliez.zelda.common.log;

import org.apache.log4j.PatternLayout;

/**
 * @author: z.j.
 * @time: 2020-11-27 20:35
 */
public class MyPatternLayout extends PatternLayout {
    private String Header = null;

    public MyPatternLayout() {
    }

    public MyPatternLayout(String arg0) {
        super(arg0);
    }

    public void SetHeader(String _header) {
        this.Header = _header;
    }

    public String getHeader() {
        return this.Header;
    }


}
