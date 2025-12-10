package com.blockchain.blockchain_service.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.8.
 */
@SuppressWarnings("rawtypes")
public class CustomerContract extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506119e6806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063518bddd914610051578063583f207b1461007a578063b9a852ab1461008f578063c9b8deef146100a4575b600080fd5b61006461005f366004611117565b6100b7565b60405161007191906112ae565b60405180910390f35b61008d610088366004611600565b610acb565b005b610097610d20565b6040516100719190611792565b61008d6100b2366004611600565b610df9565b6100bf611086565b60008290036100e95760405162461bcd60e51b81526004016100e0906117f4565b60405180910390fd5b600083836040516100fb929190611821565b9081526020016040518091039020600101805461011790611831565b90506000036101635760405162461bcd60e51b815260206004820152601860248201527710dd5cdd1bdb595c880a139252ca481b9bdd08199bdd5b9960421b60448201526064016100e0565b60008383604051610175929190611821565b90815260200160405180910390206040518061012001604052908160008201805461019f90611831565b80601f01602080910402602001604051908101604052809291908181526020018280546101cb90611831565b80156102185780601f106101ed57610100808354040283529160200191610218565b820191906000526020600020905b8154815290600101906020018083116101fb57829003601f168201915b5050505050815260200160018201805461023190611831565b80601f016020809104026020016040519081016040528092919081815260200182805461025d90611831565b80156102aa5780601f1061027f576101008083540402835291602001916102aa565b820191906000526020600020905b81548152906001019060200180831161028d57829003601f168201915b505050505081526020016002820180546102c390611831565b80601f01602080910402602001604051908101604052809291908181526020018280546102ef90611831565b801561033c5780601f106103115761010080835404028352916020019161033c565b820191906000526020600020905b81548152906001019060200180831161031f57829003601f168201915b5050505050815260200160038201805461035590611831565b80601f016020809104026020016040519081016040528092919081815260200182805461038190611831565b80156103ce5780601f106103a3576101008083540402835291602001916103ce565b820191906000526020600020905b8154815290600101906020018083116103b157829003601f168201915b505050505081526020016004820180546103e790611831565b80601f016020809104026020016040519081016040528092919081815260200182805461041390611831565b80156104605780601f1061043557610100808354040283529160200191610460565b820191906000526020600020905b81548152906001019060200180831161044357829003601f168201915b5050505050815260200160058201805461047990611831565b80601f01602080910402602001604051908101604052809291908181526020018280546104a590611831565b80156104f25780601f106104c7576101008083540402835291602001916104f2565b820191906000526020600020905b8154815290600101906020018083116104d557829003601f168201915b5050505050815260200160068201805461050b90611831565b80601f016020809104026020016040519081016040528092919081815260200182805461053790611831565b80156105845780601f1061055957610100808354040283529160200191610584565b820191906000526020600020905b81548152906001019060200180831161056757829003601f168201915b5050505050815260200160078201805461059d90611831565b80601f01602080910402602001604051908101604052809291908181526020018280546105c990611831565b80156106165780601f106105eb57610100808354040283529160200191610616565b820191906000526020600020905b8154815290600101906020018083116105f957829003601f168201915b50505050508152602001600882016040518061010001604052908160008201805461064090611831565b80601f016020809104026020016040519081016040528092919081815260200182805461066c90611831565b80156106b95780601f1061068e576101008083540402835291602001916106b9565b820191906000526020600020905b81548152906001019060200180831161069c57829003601f168201915b505050505081526020016001820180546106d290611831565b80601f01602080910402602001604051908101604052809291908181526020018280546106fe90611831565b801561074b5780601f106107205761010080835404028352916020019161074b565b820191906000526020600020905b81548152906001019060200180831161072e57829003601f168201915b5050505050815260200160028201805461076490611831565b80601f016020809104026020016040519081016040528092919081815260200182805461079090611831565b80156107dd5780601f106107b2576101008083540402835291602001916107dd565b820191906000526020600020905b8154815290600101906020018083116107c057829003601f168201915b505050505081526020016003820180546107f690611831565b80601f016020809104026020016040519081016040528092919081815260200182805461082290611831565b801561086f5780601f106108445761010080835404028352916020019161086f565b820191906000526020600020905b81548152906001019060200180831161085257829003601f168201915b5050505050815260200160048201805461088890611831565b80601f01602080910402602001604051908101604052809291908181526020018280546108b490611831565b80156109015780601f106108d657610100808354040283529160200191610901565b820191906000526020600020905b8154815290600101906020018083116108e457829003601f168201915b5050505050815260200160058201805461091a90611831565b80601f016020809104026020016040519081016040528092919081815260200182805461094690611831565b80156109935780601f1061096857610100808354040283529160200191610993565b820191906000526020600020905b81548152906001019060200180831161097657829003601f168201915b505050505081526020016006820180546109ac90611831565b80601f01602080910402602001604051908101604052809291908181526020018280546109d890611831565b8015610a255780601f106109fa57610100808354040283529160200191610a25565b820191906000526020600020905b815481529060010190602001808311610a0857829003601f168201915b50505050508152602001600782018054610a3e90611831565b80601f0160208091040260200160405190810160405280929190818152602001828054610a6a90611831565b8015610ab75780601f10610a8c57610100808354040283529160200191610ab7565b820191906000526020600020905b815481529060010190602001808311610a9a57829003601f168201915b505050919092525050509052509392505050565b806020015151600003610af05760405162461bcd60e51b81526004016100e0906117f4565b60008160200151604051610b04919061186b565b90815260200160405180910390206001018054610b2090611831565b9050600003610b6c5760405162461bcd60e51b815260206004820152601860248201527710dd5cdd1bdb595c880a139252ca481b9bdd08199bdd5b9960421b60448201526064016100e0565b8060008260200151604051610b81919061186b565b90815260405190819003602001902081518190610b9e90826118d6565b5060208201516001820190610bb390826118d6565b5060408201516002820190610bc890826118d6565b5060608201516003820190610bdd90826118d6565b5060808201516004820190610bf290826118d6565b5060a08201516005820190610c0790826118d6565b5060c08201516006820190610c1c90826118d6565b5060e08201516007820190610c3190826118d6565b50610100820151805160088301908190610c4b90826118d6565b5060208201516001820190610c6090826118d6565b5060408201516002820190610c7590826118d6565b5060608201516003820190610c8a90826118d6565b5060808201516004820190610c9f90826118d6565b5060a08201516005820190610cb490826118d6565b5060c08201516006820190610cc990826118d6565b5060e08201516007820190610cde90826118d6565b505083516040517f7840905d4fe3802e63c858aadc77c56119def4d800a50b0d896b9d4d29c4616b9450610d159350909150611996565b60405180910390a150565b60606001805480602002602001604051908101604052809291908181526020016000905b82821015610df0578382906000526020600020018054610d6390611831565b80601f0160208091040260200160405190810160405280929190818152602001828054610d8f90611831565b8015610ddc5780601f10610db157610100808354040283529160200191610ddc565b820191906000526020600020905b815481529060010190602001808311610dbf57829003601f168201915b505050505081526020019060010190610d44565b50505050905090565b806020015151600003610e1e5760405162461bcd60e51b81526004016100e0906117f4565b60008160200151604051610e32919061186b565b90815260200160405180910390206001018054610e4e90611831565b159050610e9d5760405162461bcd60e51b815260206004820152601d60248201527f437573746f6d657220284e494b2920616c72656164792065786973747300000060448201526064016100e0565b8060008260200151604051610eb2919061186b565b90815260405190819003602001902081518190610ecf90826118d6565b5060208201516001820190610ee490826118d6565b5060408201516002820190610ef990826118d6565b5060608201516003820190610f0e90826118d6565b5060808201516004820190610f2390826118d6565b5060a08201516005820190610f3890826118d6565b5060c08201516006820190610f4d90826118d6565b5060e08201516007820190610f6290826118d6565b50610100820151805160088301908190610f7c90826118d6565b5060208201516001820190610f9190826118d6565b5060408201516002820190610fa690826118d6565b5060608201516003820190610fbb90826118d6565b5060808201516004820190610fd090826118d6565b5060a08201516005820190610fe590826118d6565b5060c08201516006820190610ffa90826118d6565b5060e0820151600782019061100f90826118d6565b50505060208301516001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60192506110559150826118d6565b5080516040517f770da0fc1d1623010a5e1132aaf12c53daafda583004854febf7f9390bcb1cbd91610d1591611996565b604051806101200160405280606081526020016060815260200160608152602001606081526020016060815260200160608152602001606081526020016060815260200161111260405180610100016040528060608152602001606081526020016060815260200160608152602001606081526020016060815260200160608152602001606081525090565b905290565b6000806020838503121561112a57600080fd5b823567ffffffffffffffff8082111561114257600080fd5b818501915085601f83011261115657600080fd5b81358181111561116557600080fd5b86602082850101111561117757600080fd5b60209290920196919550909350505050565b60005b838110156111a457818101518382015260200161118c565b50506000910152565b600081518084526111c5816020860160208601611189565b601f01601f19169290920160200192915050565b600061010082518185526111ef828601826111ad565b9150506020830151848203602086015261120982826111ad565b9150506040830151848203604086015261122382826111ad565b9150506060830151848203606086015261123d82826111ad565b9150506080830151848203608086015261125782826111ad565b91505060a083015184820360a086015261127182826111ad565b91505060c083015184820360c086015261128b82826111ad565b91505060e083015184820360e08601526112a582826111ad565b95945050505050565b60208152600082516101208060208501526112cd6101408501836111ad565b91506020850151601f19808685030160408701526112eb84836111ad565b9350604087015191508086850301606087015261130884836111ad565b9350606087015191508086850301608087015261132584836111ad565b935060808701519150808685030160a087015261134284836111ad565b935060a08701519150808685030160c087015261135f84836111ad565b935060c08701519150808685030160e087015261137c84836111ad565b935060e0870151915061010081878603018188015261139b85846111ad565b9088015187820390920184880152935090506113b783826111d9565b9695505050505050565b634e487b7160e01b600052604160045260246000fd5b604051610100810167ffffffffffffffff811182821017156113fb576113fb6113c1565b60405290565b604051610120810167ffffffffffffffff811182821017156113fb576113fb6113c1565b600082601f83011261143657600080fd5b813567ffffffffffffffff80821115611451576114516113c1565b604051601f8301601f19908116603f01168101908282118183101715611479576114796113c1565b8160405283815286602085880101111561149257600080fd5b836020870160208301376000602085830101528094505050505092915050565b600061010082840312156114c557600080fd5b6114cd6113d7565b9050813567ffffffffffffffff808211156114e757600080fd5b6114f385838601611425565b8352602084013591508082111561150957600080fd5b61151585838601611425565b6020840152604084013591508082111561152e57600080fd5b61153a85838601611425565b6040840152606084013591508082111561155357600080fd5b61155f85838601611425565b6060840152608084013591508082111561157857600080fd5b61158485838601611425565b608084015260a084013591508082111561159d57600080fd5b6115a985838601611425565b60a084015260c08401359150808211156115c257600080fd5b6115ce85838601611425565b60c084015260e08401359150808211156115e757600080fd5b506115f484828501611425565b60e08301525092915050565b60006020828403121561161257600080fd5b813567ffffffffffffffff8082111561162a57600080fd5b90830190610120828603121561163f57600080fd5b611647611401565b82358281111561165657600080fd5b61166287828601611425565b82525060208301358281111561167757600080fd5b61168387828601611425565b60208301525060408301358281111561169b57600080fd5b6116a787828601611425565b6040830152506060830135828111156116bf57600080fd5b6116cb87828601611425565b6060830152506080830135828111156116e357600080fd5b6116ef87828601611425565b60808301525060a08301358281111561170757600080fd5b61171387828601611425565b60a08301525060c08301358281111561172b57600080fd5b61173787828601611425565b60c08301525060e08301358281111561174f57600080fd5b61175b87828601611425565b60e083015250610100808401358381111561177557600080fd5b611781888287016114b2565b918301919091525095945050505050565b6000602080830181845280855180835260408601915060408160051b870101925083870160005b828110156117e757603f198886030184526117d58583516111ad565b945092850192908501906001016117b9565b5092979650505050505050565b6020808252601390820152724e494b2063616e6e6f7420626520656d70747960681b604082015260600190565b8183823760009101908152919050565b600181811c9082168061184557607f821691505b60208210810361186557634e487b7160e01b600052602260045260246000fd5b50919050565b6000825161187d818460208701611189565b9190910192915050565b601f8211156118d157600081815260208120601f850160051c810160208610156118ae5750805b601f850160051c820191505b818110156118cd578281556001016118ba565b5050505b505050565b815167ffffffffffffffff8111156118f0576118f06113c1565b611904816118fe8454611831565b84611887565b602080601f83116001811461193957600084156119215750858301515b600019600386901b1c1916600185901b1785556118cd565b600085815260208120601f198616915b8281101561196857888601518255948401946001909101908401611949565b50858210156119865787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6020815260006119a960208301846111ad565b939250505056fea2646970667358221220d3c5bb90c1d9485dde37cb74184f3c81896ea1f74753abf947adcfe9321b838864736f6c63430008140033";

    public static final String FUNC_CREATECUSTOMER = "createCustomer";

    public static final String FUNC_UPDATECUSTOMER = "updateCustomer";

    public static final String FUNC_GETCUSTOMER = "getCustomer";

    public static final String FUNC_GETALLCUSTOMERNIKS = "getAllCustomerNiks";

    public static final Event CUSTOMERCREATED_EVENT = new Event("CustomerCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CUSTOMERUPDATED_EVENT = new Event("CustomerUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected CustomerContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CustomerContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CustomerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CustomerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CustomerCreatedEventResponse> getCustomerCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CUSTOMERCREATED_EVENT, transactionReceipt);
        ArrayList<CustomerCreatedEventResponse> responses = new ArrayList<CustomerCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CustomerCreatedEventResponse typedResponse = new CustomerCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CustomerCreatedEventResponse getCustomerCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CUSTOMERCREATED_EVENT, log);
        CustomerCreatedEventResponse typedResponse = new CustomerCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CustomerCreatedEventResponse> customerCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCustomerCreatedEventFromLog(log));
    }

    public Flowable<CustomerCreatedEventResponse> customerCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CUSTOMERCREATED_EVENT));
        return customerCreatedEventFlowable(filter);
    }

    public static List<CustomerUpdatedEventResponse> getCustomerUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CUSTOMERUPDATED_EVENT, transactionReceipt);
        ArrayList<CustomerUpdatedEventResponse> responses = new ArrayList<CustomerUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CustomerUpdatedEventResponse typedResponse = new CustomerUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CustomerUpdatedEventResponse getCustomerUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CUSTOMERUPDATED_EVENT, log);
        CustomerUpdatedEventResponse typedResponse = new CustomerUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CustomerUpdatedEventResponse> customerUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCustomerUpdatedEventFromLog(log));
    }

    public Flowable<CustomerUpdatedEventResponse> customerUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CUSTOMERUPDATED_EVENT));
        return customerUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createCustomer(Customer data) {
        final Function function = new Function(
                FUNC_CREATECUSTOMER, 
                Arrays.<Type>asList(data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCustomer(Customer data) {
        final Function function = new Function(
                FUNC_UPDATECUSTOMER, 
                Arrays.<Type>asList(data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Customer> getCustomer(String nik) {
        final Function function = new Function(FUNC_GETCUSTOMER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(nik)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Customer>() {}));
        return executeRemoteCallSingleValueReturn(function, Customer.class);
    }

    public RemoteFunctionCall<List> getAllCustomerNiks() {
        final Function function = new Function(FUNC_GETALLCUSTOMERNIKS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static CustomerContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CustomerContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CustomerContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CustomerContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CustomerContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CustomerContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CustomerContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CustomerContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CustomerContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CustomerContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CustomerContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CustomerContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CustomerContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CustomerContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CustomerContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CustomerContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CustomerCid extends DynamicStruct {
        public String identityCopy;

        public String residencePermit;

        public String incomeProof;

        public String businessDocumentCopy;

        public String professionalLicense;

        public String otherBankCreditCardInfo;

        public String emeraldCustomer;

        public String taxIdNumber;

        public CustomerCid(String identityCopy, String residencePermit, String incomeProof, String businessDocumentCopy, String professionalLicense, String otherBankCreditCardInfo, String emeraldCustomer, String taxIdNumber) {
            super(new org.web3j.abi.datatypes.Utf8String(identityCopy), 
                    new org.web3j.abi.datatypes.Utf8String(residencePermit), 
                    new org.web3j.abi.datatypes.Utf8String(incomeProof), 
                    new org.web3j.abi.datatypes.Utf8String(businessDocumentCopy), 
                    new org.web3j.abi.datatypes.Utf8String(professionalLicense), 
                    new org.web3j.abi.datatypes.Utf8String(otherBankCreditCardInfo), 
                    new org.web3j.abi.datatypes.Utf8String(emeraldCustomer), 
                    new org.web3j.abi.datatypes.Utf8String(taxIdNumber));
            this.identityCopy = identityCopy;
            this.residencePermit = residencePermit;
            this.incomeProof = incomeProof;
            this.businessDocumentCopy = businessDocumentCopy;
            this.professionalLicense = professionalLicense;
            this.otherBankCreditCardInfo = otherBankCreditCardInfo;
            this.emeraldCustomer = emeraldCustomer;
            this.taxIdNumber = taxIdNumber;
        }

        public CustomerCid(Utf8String identityCopy, Utf8String residencePermit, Utf8String incomeProof, Utf8String businessDocumentCopy, Utf8String professionalLicense, Utf8String otherBankCreditCardInfo, Utf8String emeraldCustomer, Utf8String taxIdNumber) {
            super(identityCopy, residencePermit, incomeProof, businessDocumentCopy, professionalLicense, otherBankCreditCardInfo, emeraldCustomer, taxIdNumber);
            this.identityCopy = identityCopy.getValue();
            this.residencePermit = residencePermit.getValue();
            this.incomeProof = incomeProof.getValue();
            this.businessDocumentCopy = businessDocumentCopy.getValue();
            this.professionalLicense = professionalLicense.getValue();
            this.otherBankCreditCardInfo = otherBankCreditCardInfo.getValue();
            this.emeraldCustomer = emeraldCustomer.getValue();
            this.taxIdNumber = taxIdNumber.getValue();
        }
    }

    public static class Customer extends DynamicStruct {
        public String id;

        public String nik;

        public String fullName;

        public String birthDate;

        public String gender;

        public String nationality;

        public String fullAddress;

        public String phoneNumber;

        public CustomerCid cid;

        public Customer(String id, String nik, String fullName, String birthDate, String gender, String nationality, String fullAddress, String phoneNumber, CustomerCid cid) {
            super(new org.web3j.abi.datatypes.Utf8String(id), 
                    new org.web3j.abi.datatypes.Utf8String(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.Utf8String(birthDate), 
                    new org.web3j.abi.datatypes.Utf8String(gender), 
                    new org.web3j.abi.datatypes.Utf8String(nationality), 
                    new org.web3j.abi.datatypes.Utf8String(fullAddress), 
                    new org.web3j.abi.datatypes.Utf8String(phoneNumber), 
                    cid);
            this.id = id;
            this.nik = nik;
            this.fullName = fullName;
            this.birthDate = birthDate;
            this.gender = gender;
            this.nationality = nationality;
            this.fullAddress = fullAddress;
            this.phoneNumber = phoneNumber;
            this.cid = cid;
        }

        public Customer(Utf8String id, Utf8String nik, Utf8String fullName, Utf8String birthDate, Utf8String gender, Utf8String nationality, Utf8String fullAddress, Utf8String phoneNumber, CustomerCid cid) {
            super(id, nik, fullName, birthDate, gender, nationality, fullAddress, phoneNumber, cid);
            this.id = id.getValue();
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.birthDate = birthDate.getValue();
            this.gender = gender.getValue();
            this.nationality = nationality.getValue();
            this.fullAddress = fullAddress.getValue();
            this.phoneNumber = phoneNumber.getValue();
            this.cid = cid;
        }
    }

    public static class CustomerCreatedEventResponse extends BaseEventResponse {
        public String id;
    }

    public static class CustomerUpdatedEventResponse extends BaseEventResponse {
        public String id;
    }
}
