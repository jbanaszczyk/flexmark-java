package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.LineFormattingAppendable;
import com.vladsch.flexmark.util.html.LineFormattingAppendableImpl;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class MarkdownWriter implements LineFormattingAppendable {
    private final LineFormattingAppendable myAppendable;
    private NodeFormatterContext context;

    public MarkdownWriter() {
        this(0);
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }

    public MarkdownWriter(int formatOptions) {
        myAppendable = new LineFormattingAppendableImpl(formatOptions);
        myAppendable.setOptions(myAppendable.getOptions() | LineFormattingAppendable.PREFIX_PRE_FORMATTED);
    }

    public void setContext(NodeFormatterContext context) {
        this.context = context;
    }

    public NodeFormatterContext getContext() {
        return context;
    }

    public MarkdownWriter tailBlankLine() {
        return tailBlankLine(1);
    }

    public boolean isLastBlockQuoteChild(Node node) {
        Node parent = node.getParent();
        return parent instanceof BlockQuote && parent.getLastChild() == node;
    }

    public MarkdownWriter tailBlankLine(int count) {
        Node node = context.getCurrentNode();
        if (isLastBlockQuoteChild(node)) {
            // Needed to not add block quote prefix to trailing blank lines
            CharSequence prefix = getPrefix();
            popPrefix();
            blankLine(count);
            pushPrefix();
            setPrefix(prefix, false);
        } else {
            blankLine(count);
        }
        return this;
    }

    public MarkdownWriter appendNonTranslating(final CharSequence csq) {
        return appendNonTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq) {
        return appendNonTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix) {
        return appendNonTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2, Consumer<String> placeholderConsumer) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendTranslating(final CharSequence csq) {
        return appendTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq) {
        return appendTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix) {
        return appendTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformTranslating(prefix, csq, suffix, suffix2));
        } else {
            if (prefix != null) append(prefix);
            append(csq);
            if (suffix != null) append(suffix);
        }
        return this;
    }

    // @formatter:off
    @Override public boolean isPreFormatted()                                                                                   { return myAppendable.isPreFormatted(); }
    @Override public CharSequence getIndentPrefix()                                                                             { return myAppendable.getIndentPrefix(); }
    @Override public CharSequence getPrefix()                                                                                   { return myAppendable.getPrefix(); }
    @Override public int getLineCount()                                                                                         { return myAppendable.getLineCount(); }
    @Override public int offset()                                                                                               { return myAppendable.offset(); }
    @Override public int offsetWithPending()                                                                                    { return myAppendable.offsetWithPending(); }
    @Override public int column()                                                                                               { return myAppendable.column(); }
    @Override public int getOptions()                                                                                           { return myAppendable.getOptions(); }
    @Override public List<CharSequence> getLines(final int startLine, final int endLine)                                        {return myAppendable.getLines(startLine, endLine);}
    @Override public List<CharSequence> getLineContents(final int startLine, final int endLine)                                 {return myAppendable.getLineContents(startLine, endLine);}
    @Override public List<BasedSequence> getLinePrefixes(final int startLine, final int endLine)                                {return myAppendable.getLinePrefixes(startLine, endLine);}
    @Override public String toString(final int maxBlankLines)                                                                   {return myAppendable.toString(maxBlankLines);}
    @Override public boolean isPendingSpace()                                                                                   {return myAppendable.isPendingSpace();}
    @Override public int getPendingEOL()                                                                                        {return myAppendable.getPendingEOL();}
    @Override public int getPendingSpace()                                                                                      {return myAppendable.getPendingSpace();}
    @Override public boolean isPreFormattedLine(final int line)                                                                 {return myAppendable.isPreFormattedLine(line);}

    @Override public MarkdownWriter addLine()                                                                                   { myAppendable.addLine(); return this; }
    @Override public MarkdownWriter addPrefix(final CharSequence prefix)                                                        { myAppendable.addPrefix(prefix); return this; }
    @Override public MarkdownWriter addPrefix(final CharSequence prefix, boolean afterEol)                                      { myAppendable.addPrefix(prefix, afterEol); return this; }
    @Override public MarkdownWriter append(final char c)                                                                        { myAppendable.append(c); return this; }
    @Override public MarkdownWriter append(final CharSequence csq)                                                              { myAppendable.append(csq); return this; }
    @Override public MarkdownWriter append(final CharSequence csq, final int start, final int end)                              { myAppendable.append(csq, start, end); return this; }
    @Override public MarkdownWriter blankLine()                                                                                 { myAppendable.blankLine(); return this; }
    @Override public MarkdownWriter blankLine(final int count)                                                                  { myAppendable.blankLine(count); return this; }
    @Override public MarkdownWriter blankLineIf(final boolean predicate)                                                        { myAppendable.blankLineIf(predicate); return this; }
    @Override public MarkdownWriter closePreFormatted()                                                                         { myAppendable.closePreFormatted(); return this; }
    @Override public MarkdownWriter indent()                                                                                    { myAppendable.indent(); return this; }
    @Override public MarkdownWriter line()                                                                                      { myAppendable.line(); return this; }
    @Override public MarkdownWriter lineIf(final boolean predicate)                                                             { myAppendable.lineIf(predicate); return this; }
    @Override public MarkdownWriter openPreFormatted(final boolean keepIndent)                                                  { myAppendable.openPreFormatted(keepIndent); return this; }
    @Override public MarkdownWriter popPrefix()                                                                                 { myAppendable.popPrefix(); return this; }
    @Override public MarkdownWriter popPrefix(boolean afterEol)                                                                 { myAppendable.popPrefix(afterEol); return this; }
    @Override public MarkdownWriter pushPrefix()                                                                                { myAppendable.pushPrefix(); return this; }
    @Override public MarkdownWriter repeat(final char c, final int count)                                                       { myAppendable.repeat(c, count); return this; }
    @Override public MarkdownWriter repeat(final CharSequence csq, final int count)                                             { myAppendable.repeat(csq, count); return this; }
    @Override public MarkdownWriter repeat(final CharSequence csq, final int start, final int end, final int count)             { myAppendable.repeat(csq, start, end, count); return this; }
    @Override public MarkdownWriter setIndentPrefix(final CharSequence prefix)                                                  { myAppendable.setIndentPrefix(prefix); return this; }
    @Override public MarkdownWriter setOptions(final int options)                                                               { myAppendable.setOptions(options); return this; }
    @Override public MarkdownWriter setPrefix(final CharSequence prefix)                                                        { myAppendable.setPrefix(prefix); return this; }
    @Override public MarkdownWriter setPrefix(final CharSequence prefix, boolean afterEol)                                      { myAppendable.setPrefix(prefix, afterEol); return this; }
    @Override public MarkdownWriter unIndent()                                                                                  { myAppendable.unIndent(); return this; }
    @Override public MarkdownWriter append(final LineFormattingAppendable lineAppendable, final int startLine, final int endLine) {myAppendable.append(lineAppendable, startLine, endLine); return this;}
    @Override public MarkdownWriter prefixLines(final CharSequence prefix, final boolean addAfterLinePrefix, final int startLine, final int endLine)                              {myAppendable.prefixLines(prefix, addAfterLinePrefix, startLine, endLine); return this;}
    @Override public MarkdownWriter appendTo(final Appendable out, final int maxBlankLines, final CharSequence prefix, final int startLine, final int endLine) throws IOException {myAppendable.appendTo(out, maxBlankLines, prefix, startLine, endLine); return this;}
    @Override public MarkdownWriter lineWithTrailingSpaces(int count) {myAppendable.lineWithTrailingSpaces(count); return this;}
    @Override public MarkdownWriter removeLines(final int startLine, final int endLine) {myAppendable.removeLines(startLine, endLine); return this;}
    @Override public MarkdownWriter lineOnFirstText(boolean value) {myAppendable.lineOnFirstText(value); return this;}
    @Override public MarkdownWriter addIndentOnFirstEOL(Runnable runnable) {myAppendable.addIndentOnFirstEOL(runnable); return this;}
    @Override public MarkdownWriter removeIndentOnFirstEOL(Runnable runnable) {myAppendable.removeIndentOnFirstEOL(runnable); return this;}
    @Override public MarkdownWriter unIndentNoEol() {myAppendable.unIndentNoEol(); return this;}
    // @formatter:on
    
}

