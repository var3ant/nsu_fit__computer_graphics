package FF_1234_Pupkin_Init.instruments;

import FF_1234_Pupkin_Init.InitView;

import java.awt.*;

public class InstrumentFactory {
    InitView view;
    Fill fill;
    DrawShape shape;
    Line line;
    public InstrumentFactory(InitView _view) {
        view = _view;
        line = new Line(view);
        fill = new Fill(view);
        shape = new DrawShape(view);
    }
    public Instrument getInstrument(EInstruments type) {
        Instrument toReturn = null;
        switch (type) {
            case FILL:
                toReturn = fill;
                break;
            case SHAPE:
                toReturn = shape;
                break;
            case LINE:
                toReturn = line;
                break;
            default:
                throw new IllegalArgumentException("Wrong type:" + type);
        }
        return toReturn;
    }
}
