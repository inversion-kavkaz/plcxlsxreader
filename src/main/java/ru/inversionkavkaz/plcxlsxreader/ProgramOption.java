package ru.inversionkavkaz.plcxlsxreader;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class ProgramOption extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "connection",
            abbrev = 'o',
            help = "TNS connection like user/password@base",
            category = "startup",
            defaultValue = ""
    )
    public String connection;

    @Option(
            name = "file",
            abbrev = 'f',
            help = "XLXS file path to import.",
            category = "startup",
            defaultValue = ""
    )
    public String filePath;

    @Option(
            name = "controlFile",
            abbrev = 'c',
            help = "XLXS control file xml.",
            category = "startup",
            defaultValue = ""
    )
    public String controlFile;
}
