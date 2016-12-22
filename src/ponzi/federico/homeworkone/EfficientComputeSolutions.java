package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * Created by isaacisback on 22/12/16.
 */
public class EfficientComputeSolutions extends RecursiveTask<Integer>
{
    private final EmptyCellGraph emptyCellGraph;
    private boolean isParallel = true;
    private boolean isDimezzamento = true;

    public EfficientComputeSolutions(EmptyCellGraph emptyCellGraph, boolean isParallel, boolean isDimezzamento)
    {
        this(emptyCellGraph);
        this.isParallel = isParallel;
        this.isDimezzamento = true;
    }

    public EfficientComputeSolutions(EmptyCellGraph emptyCellGraph)
    {
        this.emptyCellGraph = emptyCellGraph;
    }

    @Override protected Integer compute()
    {

        Coordinates coordinates = emptyCellGraph.getSmaller();
        if (emptyCellGraph.getSmaller() == null)
        {
            // Le celle sono state tutte riempite. Esco.
            return 1;
        }
        ArrayList<Integer> candidates = emptyCellGraph.getCandidates(coordinates);

        ArrayList<EfficientComputeSolutions> threads = new ArrayList<>();

        for (Integer c : candidates)
        {
            //Se impostando la cella coordinates a c, non mi porta a una configurazione non valida...
            EmptyCellGraph ecg;
            if (emptyCellGraph.tryRemoveCoordinates(coordinates, c))
            {
                ecg = new EmptyCellGraph(emptyCellGraph);
                ecg.removeCoordinates(coordinates, c);

                threads.add(new EfficientComputeSolutions(ecg));
            }
        }

        if(threads.size() == 0)
        {
            return 0;
        }

        int toRet = 0;
        // Repeats some code, but simplify experiments
        if (isParallel)
        {
            if (isDimezzamento)
            {
                for(int i = 0; i < threads.size()-1; i++)
                {
                    threads.get(i).fork();
                }

                toRet += threads.get(threads.size()-1).compute();

                for(int i = 0; i < threads.size()-1; i++)
                {
                    toRet += threads.get(i).join();
                }
            }
            else
            {
                for (EfficientComputeSolutions ecs : threads)
                {
                    ecs.fork();
                }
                for (EfficientComputeSolutions ecs : threads)
                {
                    toRet += ecs.join();
                }
            }
        }
        else
        {
            for (EfficientComputeSolutions ecs : threads)
            {
                toRet += ecs.compute();
            }
        }

        return toRet;
    }
}
