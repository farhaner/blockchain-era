package com.blockchain.blockchain_service.service.host;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
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
    public static final String BINARY = "0x608060405234801561001057600080fd5b506114ef806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80636f7abc1b116100665780636f7abc1b146100ff5780638764194b146101125780638f360e5914610127578063d28dc66e1461013a578063eb8327e61461014d57600080fd5b80630fc6fd5a1461009857806312756754146100c15780632106ae74146100e257806363b11792146100f7575b600080fd5b6100ab6100a6366004610dd0565b610173565b6040516100b89190610e87565b60405180910390f35b6100d46100cf366004610dd0565b610291565b6040519081526020016100b8565b6100f56100f0366004610dd0565b6102d8565b005b6100ab6103ce565b6100f561010d366004610eb9565b6104a7565b61011a610755565b6040516100b89190610eff565b6100f5610135366004610f43565b6107ad565b6100d4610148366004610eb9565b61089d565b61016061015b366004610dd0565b610aec565b6040516100b89796959493929190610fbe565b60008181526020819052604090206007015460609060ff166101b05760405162461bcd60e51b81526004016101a790611021565b60405180910390fd5b60008281526020818152604080832060030180548251818502810185019093528083529193909284015b828210156102865783829060005260206000200180546101f99061104c565b80601f01602080910402602001604051908101604052809291908181526020018280546102259061104c565b80156102725780601f1061024757610100808354040283529160200191610272565b820191906000526020600020905b81548152906001019060200180831161025557829003601f168201915b5050505050815260200190600101906101da565b505050509050919050565b60008181526020819052604081206007015460ff166102c25760405162461bcd60e51b81526004016101a790611021565b5060009081526020819052604090206003015490565b60008181526020819052604090206007015460ff166103095760405162461bcd60e51b81526004016101a790611021565b6000818152602081905260409020600401546001600160a01b031633146103425760405162461bcd60e51b81526004016101a790611080565b600081815260208190526040812060078101805460ff191690556004810180546001600160a01b031916905542600682015590610383906003830190610d44565b336001600160a01b0316827fbd4fe1836afba3cc8250e09325a71792be4248b820578f2a31575e0096091f5d83600201426040516103c29291906110a7565b60405180910390a35050565b60606002805480602002602001604051908101604052809291908181526020016000905b8282101561049e5783829060005260206000200180546104119061104c565b80601f016020809104026020016040519081016040528092919081815260200182805461043d9061104c565b801561048a5780601f1061045f5761010080835404028352916020019161048a565b820191906000526020600020905b81548152906001019060200180831161046d57829003601f168201915b5050505050815260200190600101906103f2565b50505050905090565b60008281526020819052604090206007015460ff166104d85760405162461bcd60e51b81526004016101a790611021565b6000828152602081905260409020600401546001600160a01b031633146105115760405162461bcd60e51b81526004016101a790611080565b60008281526020818152604080832060018101549093926001600160401b03909116916105439190860190860161113b565b6001600160401b031614801591506106e15760078201805460ff1916905582356000818152602081815260409182902092835561058491860190860161113b565b60018201805467ffffffffffffffff19166001600160401b03929092169190911790556105b46040850185611164565b60028301916105c4919083611216565b506004810180546001600160a01b031916331790556005808401549082015542600682015560078101805460ff1916600117905560005b60038401548110156106595781600301846003018281548110610620576106206112d6565b6000918252602080832084546001810186559484529220909201916106469101826112ec565b5080610651816113c8565b9150506105fb565b5060018054808201825560009190915284357fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf69091018190553390867f085e9eb9ca5ba2cb11cc59d2c1972650e7fa6a0999896aea24bd1374274db1a66106c36040890189611164565b426040516106d393929190611418565b60405180910390a45061074f565b6106ee6040840184611164565b60028401916106fe919083611216565b504260068301553384807f085e9eb9ca5ba2cb11cc59d2c1972650e7fa6a0999896aea24bd1374274db1a66107366040880188611164565b4260405161074693929190611418565b60405180910390a45b50505050565b606060018054806020026020016040519081016040528092919081815260200182805480156107a357602002820191906000526020600020905b81548152602001906001019080831161078f575b5050505050905090565b60008381526020819052604090206007015460ff166107de5760405162461bcd60e51b81526004016101a790611021565b6000838152602081905260409020600401546001600160a01b031633146108175760405162461bcd60e51b81526004016101a790611080565b60008381526020818152604082206003018054600181018255908352912001610841828483611216565b504260008085815260200190815260200160002060060181905550827f87613f9e8a7350e4ffe9c0b932ccf24cc0d220bf174d53bc08891cee792ab2ea83834260405161089093929190611418565b60405180910390a2505050565b60008281526020819052604081206007015460ff16156108f85760405162461bcd60e51b81526020600482015260166024820152754e61736162616820616c72656164792065786973747360501b60448201526064016101a7565b600083815260208181526040909120848155906109179084018461113b565b60018201805467ffffffffffffffff19166001600160401b03929092169190911790556109476020840184611164565b6002830191610957919083611216565b506004810180546001600160a01b031916331790554260058201819055600682015560078101805460ff1916600117905560005b610998604085018561143c565b9050811015610a5957600382016109b2604086018661143c565b838181106109c2576109c26112d6565b90506020028101906109d49190611164565b825460018101845560009384526020909320909201916109f49183611216565b506002610a04604086018661143c565b83818110610a1457610a146112d6565b9050602002810190610a269190611164565b82546001810184556000938452602090932090920191610a469183611216565b5080610a51816113c8565b91505061098b565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60184905533847f5b1ef745aab64d737360b759abe53abbc2caf54fd1f7a131e6e82322cec70d85610abe602087018761113b565b610acb6020880188611164565b42604051610adc9493929190611485565b60405180910390a3509192915050565b6000818152602081905260408120600701546060908290829082908190819060ff16610b2a5760405162461bcd60e51b81526004016101a790611021565b6000888152602081815260408083208151610100810183528154815260018201546001600160401b0316938101939093526002810180549192840191610b6f9061104c565b80601f0160208091040260200160405190810160405280929190818152602001828054610b9b9061104c565b8015610be85780601f10610bbd57610100808354040283529160200191610be8565b820191906000526020600020905b815481529060010190602001808311610bcb57829003601f168201915b5050505050815260200160038201805480602002602001604051908101604052809291908181526020016000905b82821015610cc2578382906000526020600020018054610c359061104c565b80601f0160208091040260200160405190810160405280929190818152602001828054610c619061104c565b8015610cae5780601f10610c8357610100808354040283529160200191610cae565b820191906000526020600020905b815481529060010190602001808311610c9157829003601f168201915b505050505081526020019060010190610c16565b5050509082525060048201546001600160a01b03166020808301919091526005830154604080840191909152600684015460608085019190915260079094015460ff16151560809384015284519085015191850151938501519285015160a086015160c090960151919f929e50939c50919a5091985091965090945092505050565b5080546000825590600052602060002090810190610d629190610d65565b50565b80821115610d82576000610d798282610d86565b50600101610d65565b5090565b508054610d929061104c565b6000825580601f10610da2575050565b601f016020900490600052602060002090810190610d6291905b80821115610d825760008155600101610dbc565b600060208284031215610de257600080fd5b5035919050565b6000815180845260005b81811015610e0f57602081850181015186830182015201610df3565b506000602082860101526020601f19601f83011685010191505092915050565b600082825180855260208086019550808260051b84010181860160005b84811015610e7a57601f19868403018952610e68838351610de9565b98840198925090830190600101610e4c565b5090979650505050505050565b602081526000610e9a6020830184610e2f565b9392505050565b600060608284031215610eb357600080fd5b50919050565b60008060408385031215610ecc57600080fd5b8235915060208301356001600160401b03811115610ee957600080fd5b610ef585828601610ea1565b9150509250929050565b6020808252825182820181905260009190848201906040850190845b81811015610f3757835183529284019291840191600101610f1b565b50909695505050505050565b600080600060408486031215610f5857600080fd5b8335925060208401356001600160401b0380821115610f7657600080fd5b818601915086601f830112610f8a57600080fd5b813581811115610f9957600080fd5b876020828501011115610fab57600080fd5b6020830194508093505050509250925092565b87815260e060208201526000610fd760e0830189610de9565b6001600160401b03881660408401528281036060840152610ff88188610e2f565b6001600160a01b03969096166080840152505060a081019290925260c090910152949350505050565b60208082526011908201527013985cd858985a081b9bdd08199bdd5b99607a1b604082015260600190565b600181811c9082168061106057607f821691505b602082108103610eb357634e487b7160e01b600052602260045260246000fd5b6020808252600d908201526c2737ba103a34329037bbb732b960991b604082015260600190565b6040815260008084546110b98161104c565b80604086015260606001808416600081146110db57600181146110f557611126565b60ff1985168884015283151560051b880183019550611126565b8960005260208060002060005b8681101561111d5781548b8201870152908401908201611102565b8a018501975050505b50505050506020929092019290925292915050565b60006020828403121561114d57600080fd5b81356001600160401b0381168114610e9a57600080fd5b6000808335601e1984360301811261117b57600080fd5b8301803591506001600160401b0382111561119557600080fd5b6020019150368190038213156111aa57600080fd5b9250929050565b634e487b7160e01b600052604160045260246000fd5b601f82111561121157600081815260208120601f850160051c810160208610156111ee5750805b601f850160051c820191505b8181101561120d578281556001016111fa565b5050505b505050565b6001600160401b0383111561122d5761122d6111b1565b6112418361123b835461104c565b836111c7565b6000601f841160018114611275576000851561125d5750838201355b600019600387901b1c1916600186901b1783556112cf565b600083815260209020601f19861690835b828110156112a65786850135825560209485019460019092019101611286565b50868210156112c35760001960f88860031b161c19848701351681555b505060018560011b0183555b5050505050565b634e487b7160e01b600052603260045260246000fd5b8181036112f7575050565b611301825461104c565b6001600160401b03811115611318576113186111b1565b61132c81611326845461104c565b846111c7565b6000601f82116001811461136057600083156113485750848201545b600019600385901b1c1916600184901b1784556112cf565b600085815260209020601f19841690600086815260209020845b8381101561139a578286015482556001958601959091019060200161137a565b50858310156113b85781850154600019600388901b60f8161c191681555b5050505050600190811b01905550565b6000600182016113e857634e487b7160e01b600052601160045260246000fd5b5060010190565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b60408152600061142c6040830185876113ef565b9050826020830152949350505050565b6000808335601e1984360301811261145357600080fd5b8301803591506001600160401b0382111561146d57600080fd5b6020019150600581901b36038213156111aa57600080fd5b6001600160401b03851681526060602082015260006114a86060830185876113ef565b90508260408301529594505050505056fea26469706673582212209036806d7b2303c1ece9973520dd927dc409822b80c65fac6de75955beccaf8664736f6c63430008140033";

    public static final String FUNC_CREATENASABAH = "createNasabah";

    public static final String FUNC_GETNASABAH = "getNasabah";

    public static final String FUNC_GETALLNASABAHIDS = "getAllNasabahIds";

    public static final String FUNC_GETALLFILECIDS = "getAllFileCids";

    public static final String FUNC_UPDATENASABAH = "updateNasabah";

    public static final String FUNC_DELETENASABAH = "deleteNasabah";

    public static final String FUNC_ADDFILECID = "addFileCid";

    public static final String FUNC_GETFILECIDS = "getFileCids";

    public static final String FUNC_GETFILECOUNT = "getFileCount";

    public static final Event FILEADDED_EVENT = new Event("FileAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NASABAHCREATED_EVENT = new Event("NasabahCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NASABAHDELETED_EVENT = new Event("NasabahDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NASABAHUPDATED_EVENT = new Event("NasabahUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
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

    public static List<FileAddedEventResponse> getFileAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FILEADDED_EVENT, transactionReceipt);
        ArrayList<FileAddedEventResponse> responses = new ArrayList<FileAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FileAddedEventResponse typedResponse = new FileAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.cid = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FileAddedEventResponse getFileAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FILEADDED_EVENT, log);
        FileAddedEventResponse typedResponse = new FileAddedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.cid = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<FileAddedEventResponse> fileAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFileAddedEventFromLog(log));
    }

    public Flowable<FileAddedEventResponse> fileAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FILEADDED_EVENT));
        return fileAddedEventFlowable(filter);
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
            typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.createTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
        typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.createTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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

    public static List<NasabahDeletedEventResponse> getNasabahDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NASABAHDELETED_EVENT, transactionReceipt);
        ArrayList<NasabahDeletedEventResponse> responses = new ArrayList<NasabahDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NasabahDeletedEventResponse typedResponse = new NasabahDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.updateTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NasabahDeletedEventResponse getNasabahDeletedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NASABAHDELETED_EVENT, log);
        NasabahDeletedEventResponse typedResponse = new NasabahDeletedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.updateTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NasabahDeletedEventResponse> nasabahDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNasabahDeletedEventFromLog(log));
    }

    public Flowable<NasabahDeletedEventResponse> nasabahDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NASABAHDELETED_EVENT));
        return nasabahDeletedEventFlowable(filter);
    }

    public static List<NasabahUpdatedEventResponse> getNasabahUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NASABAHUPDATED_EVENT, transactionReceipt);
        ArrayList<NasabahUpdatedEventResponse> responses = new ArrayList<NasabahUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NasabahUpdatedEventResponse typedResponse = new NasabahUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.updateTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NasabahUpdatedEventResponse getNasabahUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NASABAHUPDATED_EVENT, log);
        NasabahUpdatedEventResponse typedResponse = new NasabahUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.fullName = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.updateTimeStamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
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

    public RemoteFunctionCall<Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger>> getNasabah(byte[] id) {
        final Function function = new Function(FUNC_GETNASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                convertToNative((List<Utf8String>) results.get(3).getValue()), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
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

    public RemoteFunctionCall<List> getAllFileCids() {
        final Function function = new Function(FUNC_GETALLFILECIDS, 
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

    public RemoteFunctionCall<TransactionReceipt> updateNasabah(byte[] oldId, NasabahUpdate data) {
        final Function function = new Function(
                FUNC_UPDATENASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(oldId), 
                data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteNasabah(byte[] id) {
        final Function function = new Function(
                FUNC_DELETENASABAH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addFileCid(byte[] id, String newCid) {
        final Function function = new Function(
                FUNC_ADDFILECID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.Utf8String(newCid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getFileCids(byte[] id) {
        final Function function = new Function(FUNC_GETFILECIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
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

    public RemoteFunctionCall<BigInteger> getFileCount(byte[] id) {
        final Function function = new Function(FUNC_GETFILECOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

        public List<String> contractFileCids;

        public NasabahInput(BigInteger nik, String fullName, List<String> contractFileCids) {
            super(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                            org.web3j.abi.datatypes.Utf8String.class,
                            org.web3j.abi.Utils.typeMap(contractFileCids, org.web3j.abi.datatypes.Utf8String.class)));
            this.nik = nik;
            this.fullName = fullName;
            this.contractFileCids = contractFileCids;
        }

        public NasabahInput(Uint64 nik, Utf8String fullName, DynamicArray<Utf8String> contractFileCids) {
            super(nik, fullName, contractFileCids);
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.contractFileCids = contractFileCids.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class NasabahUpdate extends DynamicStruct {
        public byte[] newId;

        public BigInteger nik;

        public String fullName;

        public NasabahUpdate(byte[] newId, BigInteger nik, String fullName) {
            super(new org.web3j.abi.datatypes.generated.Bytes32(newId), 
                    new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName));
            this.newId = newId;
            this.nik = nik;
            this.fullName = fullName;
        }

        public NasabahUpdate(Bytes32 newId, Uint64 nik, Utf8String fullName) {
            super(newId, nik, fullName);
            this.newId = newId.getValue();
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
        }
    }

    public static class FileAddedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String cid;

        public BigInteger timestamp;
    }

    public static class NasabahCreatedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String owner;

        public BigInteger nik;

        public String fullName;

        public BigInteger createTimeStamp;
    }

    public static class NasabahDeletedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String owner;

        public String fullName;

        public BigInteger updateTimeStamp;
    }

    public static class NasabahUpdatedEventResponse extends BaseEventResponse {
        public byte[] oldId;

        public byte[] newId;

        public String owner;

        public String fullName;

        public BigInteger updateTimeStamp;
    }
}
