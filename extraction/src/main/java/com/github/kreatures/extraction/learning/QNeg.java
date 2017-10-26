package com.github.kreatures.extraction.learning;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.accum.Min;
import org.nd4j.linalg.api.ops.impl.indexaccum.IMin;
import org.nd4j.linalg.factory.Nd4j;

import com.github.kreatures.extraction.learning.utils.ActionDecoder;
import com.github.kreatures.extraction.learning.utils.ActionEncoder;
import com.github.kreatures.extraction.learning.utils.StateEncoder;

public class QNeg<S, A> extends QNet<S, A> {

	public QNeg(MultiLayerNetwork net, StateEncoder<S> stateEncoder, ActionEncoder<A> actionEncoder, ActionDecoder<A> actionDecoder) {
		super(net, stateEncoder, actionEncoder, actionDecoder);
	}

	@Override
	protected INDArray maxVal(INDArray in) {
		return Nd4j.getExecutioner().exec(new Min(in), 1);
	}

	@Override
	protected INDArray maxIdx(INDArray in) {
		return Nd4j.getExecutioner().exec(new IMin(in), 1);
	}

	@Override
	protected double calcWeight(double reward, double gamma, double min) {
		return -0.01 * reward + gamma * min;
	}

}
