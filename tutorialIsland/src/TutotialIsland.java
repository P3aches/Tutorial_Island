
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jobs.createNewCharacter;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "P3aches" }, name = "Tutorial Island" )
public class TutotialIsland extends ActiveScript implements PaintListener {

        private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
        private Tree jobContainer = null;

        public final void provide(final Node... jobs) {
                for (final Node job : jobs) 	{
                        if(!jobsCollection.contains(job)) {
                                jobsCollection.add(job);
                        }
                }
                jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
        }

        public final void submit(final Job job) {
                getContainer().submit(job);
        }

        @Override
        public void onStart() {
                provide(new createNewCharacter());
        }

        @Override
        public int loop() {
                if (jobContainer != null) {
                        final Node job = jobContainer.state();
                        if (job != null) {
                                jobContainer.set(job);
                                getContainer().submit(job);
                                job.join();
                        }
                }
                return Random.nextInt(10, 50);
        }


		@Override
		public void onRepaint(Graphics arg0) {
			// TODO Auto-generated method stub
			
		}

}