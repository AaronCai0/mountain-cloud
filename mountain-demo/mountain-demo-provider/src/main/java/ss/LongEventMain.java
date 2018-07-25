package ss;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class LongEventMain {

	public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
		// System.out.println(event);
	}

	public static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
		event.set(buffer.getLong(0));
	}

	public static void main(String[] args) throws Exception {
		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = 1024 * 1024;

		// Construct the Disruptor
		Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE,
				ProducerType.SINGLE, new YieldingWaitStrategy());

		// Connect the handler
		disruptor.handleEventsWith(LongEventMain::handleEvent);

		// Start the Disruptor, starts all threads running
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(0, 1);
		long ti = System.currentTimeMillis();
		for (long l = 0; l < 1048576; l++) {
			ringBuffer.publishEvent(LongEventMain::translate, bb);
			// Thread.sleep(1000);
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - ti);
	}

}
