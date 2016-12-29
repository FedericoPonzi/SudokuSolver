package ponzi.federico.homeworkone;

import ponzi.federico.homeworkone.entities.Coordinates;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Federico Ponzi on 22/12/16.
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
        this.isDimezzamento = isDimezzamento;
    }

    public EfficientComputeSolutions(EmptyCellGraph emptyCellGraph)
    {
        this.emptyCellGraph = emptyCellGraph;
        Main.addWorker();
    }

    @Override protected Integer compute()
    {
        int toRet = 0;

        Coordinates coordinates = emptyCellGraph.getSmaller();

        ArrayList<Integer> candidates = emptyCellGraph.getCandidates(coordinates);
        ArrayList<EfficientComputeSolutions> threads = new ArrayList<>();

        for (Integer c : candidates)
        {
            //Se impostando la cella coordinates a c, non mi porta a una configurazione non valida...
            //If this leads to a bad configuration, skip this.
            EmptyCellGraph ecg;
            if (emptyCellGraph.tryRemoveCoordinates(coordinates, c))
            {
                ecg = new EmptyCellGraph(emptyCellGraph);
                ecg.removeCoordinates(coordinates, c);

                //If there are no more nodes in the graph we're done.
                if (ecg.ec.size() == 0)
                {
                    toRet++;
                    continue;
                }
                threads.add(new EfficientComputeSolutions(ecg, isParallel, isDimezzamento));
            }else
            {
                Main.addWorker();
            }
        }

        if(threads.size() == 0)
        {
            return toRet;
        }


        // Repeats some code, but simplify experiments
        if (isParallel)
        {
            if (isDimezzamento)
            {
                for(int i = 0; i < threads.size()-1; i++)
                {
                    threads.get(i).fork();
                    Main.addWorker();
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
                    Main.addWorker();
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
