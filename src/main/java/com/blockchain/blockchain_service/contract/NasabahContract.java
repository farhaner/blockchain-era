package com.blockchain.blockchain_service.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.context.annotation.Bean;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class NasabahContract extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5061125c806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633c24ee9d146100515780638764194b14610066578063b920f5c114610084578063eb8327e6146100a5575b600080fd5b61006461005f366004610ce1565b6100c5565b005b61006e61030a565b60405161007b9190610d30565b60405180910390f35b610097610092366004610ce1565b610362565b60405190815260200161007b565b6100b86100b3366004610d74565b6107c6565b60405161007b9190610e0d565b6000828152602081905260409020600a0154600160a01b900460ff166101265760405162461bcd60e51b815260206004820152601160248201527013985cd858985a081b9bdd08199bdd5b99607a1b60448201526064015b60405180910390fd5b6000828152602081905260409020600a01546001600160a01b0316331461017f5760405162461bcd60e51b815260206004820152600d60248201526c2737ba103a34329037bbb732b960991b604482015260640161011d565b6000828152602081815260409091209061019b90830183610f45565b60018201805467ffffffffffffffff191667ffffffffffffffff929092169190911790556101cc6020830183610f76565b60028301916101dc919083611063565b506101ea6040830183610f76565b60038301916101fa919083611063565b506102086060830183610f76565b6004830191610218919083611063565b5061022960a0830160808401611124565b60058201805460ff19166001838181111561024657610246610dd3565b021790555061025860a0830183610f76565b6006830191610268919083611063565b5061027660c0830183610f76565b6007830191610286919083611063565b50610298610100830160e08401611145565b60088201805460ff191660018360038111156102b6576102b6610dd3565b02179055506102c9610100830183610f76565b60098301916102d9919083611063565b5060405183907fd529933e37227c946b408abe88fd947d2e4a8e751eec4f9b4e09ff61bdc118d790600090a2505050565b6060600180548060200260200160405190810160405280929190818152602001828054801561035857602002820191906000526020600020905b815481526020019060010190808311610344575b5050505050905090565b6000828152602081905260408120600a0154600160a01b900460ff16156103c45760405162461bcd60e51b81526020600482015260166024820152754e61736162616820616c72656164792065786973747360501b604482015260640161011d565b6040805161018081019091528381526020808201906103e590850185610f45565b67ffffffffffffffff1681526020018380602001906104049190610f76565b8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525050509082525060200161044b6040850185610f76565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050908252506020016104926060850185610f76565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050908252506020016104dc60a0850160808601611124565b60018111156104ed576104ed610dd3565b81526020016104ff60a0850185610f76565b8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525050509082525060200161054660c0850185610f76565b8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250505090825250602001610591610100850160e08601611145565b60038111156105a2576105a2610dd3565b81526020016105b5610100850185610f76565b8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525093855250503360208085019190915260016040948501819052888452838252928490208551815590850151928101805467ffffffffffffffff191667ffffffffffffffff90941693909317909255509082015160028201906106479082611166565b506060820151600382019061065c9082611166565b50608082015160048201906106719082611166565b5060a082015160058201805460ff19166001838181111561069457610694610dd3565b021790555060c082015160068201906106ad9082611166565b5060e082015160078201906106c29082611166565b5061010082015160088201805460ff191660018360038111156106e7576106e7610dd3565b021790555061012082015160098201906107019082611166565b50610140820151600a9091018054610160909301511515600160a01b026001600160a81b03199093166001600160a01b03909216919091179190911790556001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60183905533837f499ba3f12552c4f1380bc3b540873b86597cbeb38e59f0227b0ca28dc80978676107a36020860186610f45565b60405167ffffffffffffffff909116815260200160405180910390a35090919050565b6107ce610c63565b6000828152602081905260409020600a0154600160a01b900460ff1661082a5760405162461bcd60e51b815260206004820152601160248201527013985cd858985a081b9bdd08199bdd5b99607a1b604482015260640161011d565b6000828152602081815260409182902082516101808101845281548152600182015467ffffffffffffffff1692810192909252600281018054929391929184019161087490610fda565b80601f01602080910402602001604051908101604052809291908181526020018280546108a090610fda565b80156108ed5780601f106108c2576101008083540402835291602001916108ed565b820191906000526020600020905b8154815290600101906020018083116108d057829003601f168201915b5050505050815260200160038201805461090690610fda565b80601f016020809104026020016040519081016040528092919081815260200182805461093290610fda565b801561097f5780601f106109545761010080835404028352916020019161097f565b820191906000526020600020905b81548152906001019060200180831161096257829003601f168201915b5050505050815260200160048201805461099890610fda565b80601f01602080910402602001604051908101604052809291908181526020018280546109c490610fda565b8015610a115780601f106109e657610100808354040283529160200191610a11565b820191906000526020600020905b8154815290600101906020018083116109f457829003601f168201915b5050509183525050600582015460209091019060ff166001811115610a3857610a38610dd3565b6001811115610a4957610a49610dd3565b8152602001600682018054610a5d90610fda565b80601f0160208091040260200160405190810160405280929190818152602001828054610a8990610fda565b8015610ad65780601f10610aab57610100808354040283529160200191610ad6565b820191906000526020600020905b815481529060010190602001808311610ab957829003601f168201915b50505050508152602001600782018054610aef90610fda565b80601f0160208091040260200160405190810160405280929190818152602001828054610b1b90610fda565b8015610b685780601f10610b3d57610100808354040283529160200191610b68565b820191906000526020600020905b815481529060010190602001808311610b4b57829003601f168201915b5050509183525050600882015460209091019060ff166003811115610b8f57610b8f610dd3565b6003811115610ba057610ba0610dd3565b8152602001600982018054610bb490610fda565b80601f0160208091040260200160405190810160405280929190818152602001828054610be090610fda565b8015610c2d5780601f10610c0257610100808354040283529160200191610c2d565b820191906000526020600020905b815481529060010190602001808311610c1057829003601f168201915b5050509183525050600a91909101546001600160a01b0381166020830152600160a01b900460ff16151560409091015292915050565b60405180610180016040528060008019168152602001600067ffffffffffffffff16815260200160608152602001606081526020016060815260200160006001811115610cb257610cb2610dd3565b815260606020820181905260408201819052016000815260606020820181905260006040830181905291015290565b60008060408385031215610cf457600080fd5b82359150602083013567ffffffffffffffff811115610d1257600080fd5b83016101208186031215610d2557600080fd5b809150509250929050565b6020808252825182820181905260009190848201906040850190845b81811015610d6857835183529284019291840191600101610d4c565b50909695505050505050565b600060208284031215610d8657600080fd5b5035919050565b6000815180845260005b81811015610db357602081850181015186830182015201610d97565b506000602082860101526020601f19601f83011685010191505092915050565b634e487b7160e01b600052602160045260246000fd5b60028110610df957610df9610dd3565b9052565b60048110610df957610df9610dd3565b602081528151602082015260006020830151610e35604084018267ffffffffffffffff169052565b506040830151610180806060850152610e526101a0850183610d8d565b91506060850151601f1980868503016080870152610e708483610d8d565b935060808701519150808685030160a0870152610e8d8483610d8d565b935060a08701519150610ea360c0870183610de9565b60c08701519150808685030160e0870152610ebe8483610d8d565b935060e08701519150610100818786030181880152610edd8584610d8d565b945080880151925050610120610ef581880184610dfd565b80880151925050610140818786030181880152610f128584610d8d565b94508088015192505050610160610f33818701836001600160a01b03169052565b90950151151593019290925250919050565b600060208284031215610f5757600080fd5b813567ffffffffffffffff81168114610f6f57600080fd5b9392505050565b6000808335601e19843603018112610f8d57600080fd5b83018035915067ffffffffffffffff821115610fa857600080fd5b602001915036819003821315610fbd57600080fd5b9250929050565b634e487b7160e01b600052604160045260246000fd5b600181811c90821680610fee57607f821691505b60208210810361100e57634e487b7160e01b600052602260045260246000fd5b50919050565b601f82111561105e57600081815260208120601f850160051c8101602086101561103b5750805b601f850160051c820191505b8181101561105a57828155600101611047565b5050505b505050565b67ffffffffffffffff83111561107b5761107b610fc4565b61108f836110898354610fda565b83611014565b6000601f8411600181146110c357600085156110ab5750838201355b600019600387901b1c1916600186901b17835561111d565b600083815260209020601f19861690835b828110156110f457868501358255602094850194600190920191016110d4565b50868210156111115760001960f88860031b161c19848701351681555b505060018560011b0183555b5050505050565b60006020828403121561113657600080fd5b813560028110610f6f57600080fd5b60006020828403121561115757600080fd5b813560048110610f6f57600080fd5b815167ffffffffffffffff81111561118057611180610fc4565b6111948161118e8454610fda565b84611014565b602080601f8311600181146111c957600084156111b15750858301515b600019600386901b1c1916600185901b17855561105a565b600085815260208120601f198616915b828110156111f8578886015182559484019460019091019084016111d9565b50858210156112165787850151600019600388901b60f8161c191681555b5050505050600190811b0190555056fea2646970667358221220b1665c86af73f3f44bcc7b89682447477f30de36d2270b99a99c7fac0992b38c64736f6c63430008140033";

    public static final String FUNC_CREATENASABAH = "createNasabah";

    public static final String FUNC_GETNASABAH = "getNasabah";

    public static final String FUNC_GETALLNASABAHIDS = "getAllNasabahIds";

    public static final String FUNC_UPDATENASABAH = "updateNasabah";

    public static final Event NASABAHCREATED_EVENT = new Event("NasabahCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint64>() {}));
    ;

    public static final Event NASABAHUPDATED_EVENT = new Event("NasabahUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected NasabahContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NasabahContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NasabahContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NasabahContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<NasabahCreatedEventResponse> getNasabahCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NASABAHCREATED_EVENT, transactionReceipt);
        ArrayList<NasabahCreatedEventResponse> responses = new ArrayList<NasabahCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NasabahCreatedEventResponse typedResponse = new NasabahCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.nik = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NasabahCreatedEventResponse getNasabahCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NASABAHCREATED_EVENT, log);
        NasabahCreatedEventResponse typedResponse = new NasabahCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.nik = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<NasabahCreatedEventResponse> nasabahCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNasabahCreatedEventFromLog(log));
    }

    public Flowable<NasabahCreatedEventResponse> nasabahCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NASABAHCREATED_EVENT));
        return nasabahCreatedEventFlowable(filter);
    }

    public static List<NasabahUpdatedEventResponse> getNasabahUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NASABAHUPDATED_EVENT, transactionReceipt);
        ArrayList<NasabahUpdatedEventResponse> responses = new ArrayList<NasabahUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NasabahUpdatedEventResponse typedResponse = new NasabahUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NasabahUpdatedEventResponse getNasabahUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NASABAHUPDATED_EVENT, log);
        NasabahUpdatedEventResponse typedResponse = new NasabahUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<NasabahUpdatedEventResponse> nasabahUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNasabahUpdatedEventFromLog(log));
    }

    public Flowable<NasabahUpdatedEventResponse> nasabahUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NASABAHUPDATED_EVENT));
        return nasabahUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createNasabah(byte[] id, NasabahInput data) {
        final Function function = new Function(
                FUNC_CREATENASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Nasabah> getNasabah(byte[] id) {
        final Function function = new Function(FUNC_GETNASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Nasabah>() {}));
        return executeRemoteCallSingleValueReturn(function, Nasabah.class);
    }

    public RemoteFunctionCall<List> getAllNasabahIds() {
        final Function function = new Function(FUNC_GETALLNASABAHIDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> updateNasabah(byte[] id, NasabahInput data) {
        final Function function = new Function(
                FUNC_UPDATENASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static NasabahContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NasabahContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NasabahContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NasabahContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NasabahContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NasabahContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NasabahContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NasabahContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NasabahContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NasabahContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NasabahContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NasabahContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<NasabahContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NasabahContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NasabahContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NasabahContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NasabahInput extends DynamicStruct {
        public BigInteger nik;

        public String fullName;

        public String birthPlace;

        public String birthDate;

        public BigInteger gender;

        public String religion;

        public String maritalStatus;

        public BigInteger bloodType;

        public String fullAddress;

        public NasabahInput(BigInteger nik, String fullName, String birthPlace, String birthDate, BigInteger gender, String religion, String maritalStatus, BigInteger bloodType, String fullAddress) {
            super(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.Utf8String(birthPlace), 
                    new org.web3j.abi.datatypes.Utf8String(birthDate), 
                    new org.web3j.abi.datatypes.generated.Uint8(gender), 
                    new org.web3j.abi.datatypes.Utf8String(religion), 
                    new org.web3j.abi.datatypes.Utf8String(maritalStatus), 
                    new org.web3j.abi.datatypes.generated.Uint8(bloodType), 
                    new org.web3j.abi.datatypes.Utf8String(fullAddress));
            this.nik = nik;
            this.fullName = fullName;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
            this.gender = gender;
            this.religion = religion;
            this.maritalStatus = maritalStatus;
            this.bloodType = bloodType;
            this.fullAddress = fullAddress;
        }

        public NasabahInput(Uint64 nik, Utf8String fullName, Utf8String birthPlace, Utf8String birthDate, Uint8 gender, Utf8String religion, Utf8String maritalStatus, Uint8 bloodType, Utf8String fullAddress) {
            super(nik, fullName, birthPlace, birthDate, gender, religion, maritalStatus, bloodType, fullAddress);
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.birthPlace = birthPlace.getValue();
            this.birthDate = birthDate.getValue();
            this.gender = gender.getValue();
            this.religion = religion.getValue();
            this.maritalStatus = maritalStatus.getValue();
            this.bloodType = bloodType.getValue();
            this.fullAddress = fullAddress.getValue();
        }
    }

    public static class Nasabah extends DynamicStruct {
        public byte[] id;

        public BigInteger nik;

        public String fullName;

        public String birthPlace;

        public String birthDate;

        public BigInteger gender;

        public String religion;

        public String maritalStatus;

        public BigInteger bloodType;

        public String fullAddress;

        public String owner;

        public Boolean exists;

        public Nasabah(byte[] id, BigInteger nik, String fullName, String birthPlace, String birthDate, BigInteger gender, String religion, String maritalStatus, BigInteger bloodType, String fullAddress, String owner, Boolean exists) {
            super(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                    new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.Utf8String(birthPlace), 
                    new org.web3j.abi.datatypes.Utf8String(birthDate), 
                    new org.web3j.abi.datatypes.generated.Uint8(gender), 
                    new org.web3j.abi.datatypes.Utf8String(religion), 
                    new org.web3j.abi.datatypes.Utf8String(maritalStatus), 
                    new org.web3j.abi.datatypes.generated.Uint8(bloodType), 
                    new org.web3j.abi.datatypes.Utf8String(fullAddress), 
                    new org.web3j.abi.datatypes.Address(160, owner), 
                    new org.web3j.abi.datatypes.Bool(exists));
            this.id = id;
            this.nik = nik;
            this.fullName = fullName;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
            this.gender = gender;
            this.religion = religion;
            this.maritalStatus = maritalStatus;
            this.bloodType = bloodType;
            this.fullAddress = fullAddress;
            this.owner = owner;
            this.exists = exists;
        }

        public Nasabah(Bytes32 id, Uint64 nik, Utf8String fullName, Utf8String birthPlace, Utf8String birthDate, Uint8 gender, Utf8String religion, Utf8String maritalStatus, Uint8 bloodType, Utf8String fullAddress, Address owner, Bool exists) {
            super(id, nik, fullName, birthPlace, birthDate, gender, religion, maritalStatus, bloodType, fullAddress, owner, exists);
            this.id = id.getValue();
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.birthPlace = birthPlace.getValue();
            this.birthDate = birthDate.getValue();
            this.gender = gender.getValue();
            this.religion = religion.getValue();
            this.maritalStatus = maritalStatus.getValue();
            this.bloodType = bloodType.getValue();
            this.fullAddress = fullAddress.getValue();
            this.owner = owner.getValue();
            this.exists = exists.getValue();
        }
    }

    public static class NasabahCreatedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String owner;

        public BigInteger nik;
    }

    public static class NasabahUpdatedEventResponse extends BaseEventResponse {
        public byte[] id;
    }
}
