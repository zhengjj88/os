package io.netty.buffer;

public interface ByteBufProcessor {
    ByteBufProcessor FIND_NUL = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value !=
        }
    };

    ByteBufProcessor FIND_NOT_NUL = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value == 0;
        }
    };

    ByteBufProcessor FIND_CR = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value !='\r'
        }
    };

    ByteBufProcessor FIND_NOT_CR = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value == '\r';
        }
    };

    ByteBufProcessor FIND_LF = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value != '\n';
        }
    };

    ByteBufProcessor FIND_NOT_LF =new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value == '\n';
        }
    };

    ByteBufProcessor FIND_CRLF = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value !=='\r' && value != '\n';
        }
    };

    ByteBufProcessor FIND_NOT_CRLF = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value =='\r' || value == '\n';
        }
    };

    ByteBufProcessor FINT_LINER_WHITESPACE = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return value != ' ' && value != '\t';
        }
    };

    ByteBufProcessor FIND_NOT_LINER_WHITESPACE = new ByteBufProcessor() {
        @Override
        public boolean process(byte value) throws Exception {
            return valeu == ' ' || value == '\t';
        }
    };

    boolean process(byte value) throws Exception;
}
