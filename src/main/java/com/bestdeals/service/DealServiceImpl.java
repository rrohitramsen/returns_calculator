package com.bestdeals.service;

import com.bestdeals.api.AccumulateClientReturns;
import com.bestdeals.api.CompoundInterestRequest;
import com.bestdeals.api.CurrencyType;
import com.bestdeals.api.SimpleInterestRequest;
import com.bestdeals.repository.DealsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rohitkumar on 14/08/17.
 */
@Service
public class DealServiceImpl implements DealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealServiceImpl.class);

    @Autowired
    private DealsRepository dealsRepository;

    @Override
    public void makeDeal(SimpleInterestRequest simpleInterestRequest) {

        LOGGER.info("Calculating Simple Interest "+ simpleInterestRequest);

        double simpleInterest = simpleInterestRequest.getPrincipalAmount() *
                    simpleInterestRequest.getAnnualInterestRate() *
                    simpleInterestRequest.getNumberOfYears();

        dealsRepository.saveDeal(simpleInterestRequest.getClientId(),
                convertIntoUSDollar(simpleInterestRequest.getCurrencyType(), simpleInterest));
    }

    @Override
    public void makeDeal(CompoundInterestRequest compoundInterestRequest) {

        LOGGER.info("Calculating Compound Interest "+ compoundInterestRequest);

        double compoundInterest = compoundInterestRequest.getPrincipalAmount() *
                Math.pow((1 + compoundInterestRequest.getAnnualInterestRate()/ compoundInterestRequest.getInterestCompoundPerYear()),
                        compoundInterestRequest.getAnnualInterestRate() * compoundInterestRequest.getInterestCompoundPerYear())
                - compoundInterestRequest.getPrincipalAmount();

        dealsRepository.saveDeal(compoundInterestRequest.getClientId(),
                convertIntoUSDollar(compoundInterestRequest.getCurrencyType(), compoundInterest));
    }

    @Override
    public AccumulateClientReturns getClientReturns(Integer clientId) {

        LOGGER.info("Get Accumulated Returns for client "+ clientId);

        AccumulateClientReturns accumulateClientReturns;

        Double returns = dealsRepository.getDeals(clientId);
        if ( returns == null) {
            accumulateClientReturns = new AccumulateClientReturns(
                    clientId,
                    null,
                    "Client Record Not Found"
            );
        } else {
            accumulateClientReturns = new AccumulateClientReturns(
                    clientId,
                    returns,
                    "Accumulated Client Returns"
            );
        }

        return accumulateClientReturns;
    }

    /**
     *
     * @param currencyType
     * @param returns
     * @return US Dollar converted returns.
     */
    private double convertIntoUSDollar(CurrencyType currencyType, double returns) {

        LOGGER.info("Converting  currencyType "+currencyType.getName() + "to US Dollar");
        switch (currencyType) {

            case AUD:
                returns *=   0.79151;
                break;

            case EUR:
                 returns *= 1.18260;

            case GBP:
                returns *= 1.30101;

        }

        return  returns;
    }

}
