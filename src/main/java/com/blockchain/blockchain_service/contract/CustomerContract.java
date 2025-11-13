package com.blockchain.blockchain_service.contract;

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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes10;
import org.web3j.abi.datatypes.generated.Bytes16;
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
public class CustomerContract extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b506121b0806100206000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c8063c41a6b321161008c578063ce65203311610066578063ce652033146101eb578063d8c0d655146101fe578063ddaa91e114610211578063f7b845241461022657600080fd5b8063c41a6b32146101b2578063c59afdfe146101c5578063cb27d9db146101d857600080fd5b8063929e54fb116100c8578063929e54fb1461014d5780639f48019514610160578063b541d44d14610173578063bff455811461018657600080fd5b8063465d1f55146100ef57806363b2584714610118578063873b76ff14610138575b600080fd5b6101026100fd3660046118b1565b610239565b60405161010f9190611919565b60405180910390f35b61012b6101263660046118b1565b610372565b60405161010f9190611a17565b61014b610146366004611bdb565b6109a5565b005b61014b61015b366004611bdb565b610a56565b61014b61016e366004611c6a565b610b58565b61014b610181366004611cb0565b610c24565b610199610194366004611cfc565b610ce7565b6040516001600160801b0319909116815260200161010f565b61014b6101c0366004611bdb565b611145565b61014b6101d3366004611bdb565b6111f0565b61014b6101e6366004611d70565b61129b565b61014b6101f9366004611daa565b61135a565b61014b61020c366004611bdb565b611417565b6102196114c2565b60405161010f9190611dd4565b61014b610234366004611e21565b61154c565b6001600160401b038116600090815260208190526040902060090154606090600160a01b900460ff166102875760405162461bcd60e51b815260040161027e90611e3d565b60405180910390fd5b6001600160401b03821660009081526020818152604080832060080180548251818502810185019093528083529193909284015b828210156103675783829060005260206000200180546102da90611e69565b80601f016020809104026020016040519081016040528092919081815260200182805461030690611e69565b80156103535780601f1061032857610100808354040283529160200191610353565b820191906000526020600020905b81548152906001019060200180831161033657829003601f168201915b5050505050815260200190600101906102bb565b505050509050919050565b6104126040805161022081018252600080825260208201819052606092820183905282820192909252608081018290529060a08201908152602001600081526020016000815260200160008152602001606081526020016060815260200160006001600160b01b0319168152602001606081526020016060815260200160006001600160a01b031681526020016000151581526020016000151581525090565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff166104545760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b038083166000908152602081815260409182902082516102208101845281546001600160801b0319608082901b168252600160801b90049094169184019190915260018101805491928401916104b090611e69565b80601f01602080910402602001604051908101604052809291908181526020018280546104dc90611e69565b80156105295780601f106104fe57610100808354040283529160200191610529565b820191906000526020600020905b81548152906001019060200180831161050c57829003601f168201915b5050505050815260200160028201805461054290611e69565b80601f016020809104026020016040519081016040528092919081815260200182805461056e90611e69565b80156105bb5780601f10610590576101008083540402835291602001916105bb565b820191906000526020600020905b81548152906001019060200180831161059e57829003601f168201915b5050509183525050600382015460b081901b6001600160b01b0319166020830152604090910190600160501b900460ff1660018111156105fd576105fd61197b565b600181111561060e5761060e61197b565b815260200160038201600b9054906101000a900460ff1660058111156106365761063661197b565b60058111156106475761064761197b565b815260200160038201600c9054906101000a900460ff16600181111561066f5761066f61197b565b60018111156106805761068061197b565b815260200160038201600d9054906101000a900460ff1660038111156106a8576106a861197b565b60038111156106b9576106b961197b565b81526020016004820180546106cd90611e69565b80601f01602080910402602001604051908101604052809291908181526020018280546106f990611e69565b80156107465780601f1061071b57610100808354040283529160200191610746565b820191906000526020600020905b81548152906001019060200180831161072957829003601f168201915b5050505050815260200160058201805461075f90611e69565b80601f016020809104026020016040519081016040528092919081815260200182805461078b90611e69565b80156107d85780601f106107ad576101008083540402835291602001916107d8565b820191906000526020600020905b8154815290600101906020018083116107bb57829003601f168201915b5050509183525050600682015460b01b6001600160b01b031916602082015260078201805460409092019161080c90611e69565b80601f016020809104026020016040519081016040528092919081815260200182805461083890611e69565b80156108855780601f1061085a57610100808354040283529160200191610885565b820191906000526020600020905b81548152906001019060200180831161086857829003601f168201915b5050505050815260200160088201805480602002602001604051908101604052809291908181526020016000905b8282101561095f5783829060005260206000200180546108d290611e69565b80601f01602080910402602001604051908101604052809291908181526020018280546108fe90611e69565b801561094b5780601f106109205761010080835404028352916020019161094b565b820191906000526020600020905b81548152906001019060200180831161092e57829003601f168201915b5050505050815260200190600101906108b3565b50505090825250600991909101546001600160a01b038116602083015260ff600160a01b8204811615156040840152600160a81b90910416151560609091015292915050565b6001600160401b038316600090815260208190526040902060090154600160a01b900460ff166109e75760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0383166000908152602081905260409020600901546001600160a01b03163314610a2a5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0383166000908152602081905260409020600501610a50828483611f29565b50505050565b6001600160401b038316600090815260208190526040902060090154600160a01b900460ff16610a985760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0383166000908152602081905260409020600901546001600160a01b03163314610adb5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b03831660009081526020818152604082206008018054600181018255908352912001610b0f828483611f29565b50826001600160401b03167fbae0bac536ede3761ed2939c8862be05c307117fdd1d39bbbe4de8872067f8298383604051610b4b929190611fe9565b60405180910390a2505050565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff16610b9a5760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0382166000908152602081905260409020600901546001600160a01b03163314610bdd5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0382166000908152602081905260409020600301805482919060ff60601b1916600160601b836001811115610c1b57610c1b61197b565b02179055505050565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff16610c665760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0382166000908152602081905260409020600901546001600160a01b03163314610ca95760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0382166000908152602081905260409020600301805482919060ff60581b1916600160581b836005811115610c1b57610c1b61197b565b60008080610cf860208501856118b1565b6001600160401b03168152602081019190915260400160002060090154600160a01b900460ff1615610d6c5760405162461bcd60e51b815260206004820152601760248201527f437573746f6d657220616c726561647920657869737473000000000000000000604482015260640161027e565b60008080610d7d60208601866118b1565b6001600160401b0316815260208082019290925260400160002080546001600160801b031916608087901c1781559150610db9908401846118b1565b81546001600160401b0391909116600160801b0267ffffffffffffffff60801b19909116178155610ded6020840184612018565b6001830191610dfd919083611f29565b50610e0b6040840184612018565b6002830191610e1b919083611f29565b50610e2c6080840160608501612065565b60038201805469ffffffffffffffffffff191660b09290921c919091179055610e5b60a0840160808501612080565b60038201805460ff60501b1916600160501b836001811115610e7f57610e7f61197b565b0217905550610e9460c0840160a0850161209d565b60038201805460ff60581b1916600160581b836005811115610eb857610eb861197b565b0217905550610ecd60e0840160c08501612080565b60038201805460ff60601b1916600160601b836001811115610ef157610ef161197b565b0217905550610f07610100840160e085016120b8565b81600301600d6101000a81548160ff02191690836003811115610f2c57610f2c61197b565b0217905550610f3f610100840184612018565b6004830191610f4f919083611f29565b50610f5e610120840184612018565b6005830191610f6e919083611f29565b50610f8161016084016101408501612065565b60068201805469ffffffffffffffffffff191660b09290921c919091179055610fae610160840184612018565b6007830191610fbe919083611f29565b5060098101805460ff60a01b1933166001600160a81b031990911617600160a01b179055610ff46101c084016101a085016120d9565b600982018054911515600160a81b0260ff60a81b1990921691909117905560005b6110236101808501856120f4565b9050811015611093576008820161103e6101808601866120f4565b8381811061104e5761104e61213d565b90506020028101906110609190612018565b825460018101845560009384526020909320909201916110809183611f29565b508061108b81612153565b915050611015565b5060016110a360208501856118b1565b815460018101835560009283526020928390206004820401805460039092166008026101000a6001600160401b0381810219909316939092169190910291909117905533906001600160801b03198616907f65b6952669ac761e6b9434e9e6efccf0a631f05fcec62ae601269809f384181790611122908701876118b1565b6040516001600160401b03909116815260200160405180910390a3509192915050565b6001600160401b038316600090815260208190526040902060090154600160a01b900460ff166111875760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0383166000908152602081905260409020600901546001600160a01b031633146111ca5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0383166000908152602081905260409020600101610a50828483611f29565b6001600160401b038316600090815260208190526040902060090154600160a01b900460ff166112325760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0383166000908152602081905260409020600901546001600160a01b031633146112755760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0383166000908152602081905260409020600701610a50828483611f29565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff166112dd5760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0382166000908152602081905260409020600901546001600160a01b031633146113205760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b03919091166000908152602081905260409020600601805469ffffffffffffffffffff191660b09290921c919091179055565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff1661139c5760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0382166000908152602081905260409020600901546001600160a01b031633146113df5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0390911660009081526020819052604090206009018054911515600160a81b0260ff60a81b19909216919091179055565b6001600160401b038316600090815260208190526040902060090154600160a01b900460ff166114595760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0383166000908152602081905260409020600901546001600160a01b0316331461149c5760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b0383166000908152602081905260409020600401610a50828483611f29565b6060600180548060200260200160405190810160405280929190818152602001828054801561154257602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116114ff5790505b5050505050905090565b6001600160401b038216600090815260208190526040902060090154600160a01b900460ff1661158e5760405162461bcd60e51b815260040161027e90611e3d565b6001600160401b0382166000908152602081905260409020600901546001600160a01b031633146115d15760405162461bcd60e51b815260040161027e90611e9d565b6001600160401b038216600090815260208181526040909120906115f790830183612018565b6001830191611607919083611f29565b5061161860c0830160a0840161209d565b60038201805460ff60581b1916600160581b83600581111561163c5761163c61197b565b021790555061165160e0830160c08401612080565b60038201805460ff60601b1916600160601b8360018111156116755761167561197b565b0217905550611688610100830183612018565b6004830191611698919083611f29565b506116a7610120830183612018565b60058301916116b7919083611f29565b506116ca61016083016101408401612065565b60068201805469ffffffffffffffffffff191660b09290921c9190911790556116f7610160830183612018565b6007830191611707919083611f29565b5061171a6101c083016101a084016120d9565b600982018054911515600160a81b0260ff60a81b19909216919091179055611746600882016000611809565b60005b6117576101808401846120f4565b90508110156117c757600882016117726101808501856120f4565b838181106117825761178261213d565b90506020028101906117949190612018565b825460018101845560009384526020909320909201916117b49183611f29565b50806117bf81612153565b915050611749565b50805460405160809190911b6001600160801b031916907fa8ccd1b91fc9ad804a93af37e08375ba93d2a5ffbb001f6456c07561ad484a9090600090a2505050565b5080546000825590600052602060002090810190611827919061182a565b50565b8082111561184757600061183e828261184b565b5060010161182a565b5090565b50805461185790611e69565b6000825580601f10611867575050565b601f01602090049060005260206000209081019061182791905b808211156118475760008155600101611881565b80356001600160401b03811681146118ac57600080fd5b919050565b6000602082840312156118c357600080fd5b6118cc82611895565b9392505050565b6000815180845260005b818110156118f9576020818501810151868301820152016118dd565b506000602082860101526020601f19601f83011685010191505092915050565b6000602080830181845280855180835260408601915060408160051b870101925083870160005b8281101561196e57603f1988860301845261195c8583516118d3565b94509285019290850190600101611940565b5092979650505050505050565b634e487b7160e01b600052602160045260246000fd5b600281106119a1576119a161197b565b9052565b600681106119a1576119a161197b565b600481106119a1576119a161197b565b6000815180845260208085019450848260051b860182860160005b85811015611a0a5783830389526119f88383516118d3565b988501989250908401906001016119e0565b5090979650505050505050565b60208152611a326020820183516001600160801b0319169052565b60006020830151611a4e60408401826001600160401b03169052565b506040830151610220806060850152611a6b6102408501836118d3565b91506060850151601f1980868503016080870152611a8984836118d3565b935060808701519150611aa860a08701836001600160b01b0319169052565b60a08701519150611abc60c0870183611991565b60c08701519150611ad060e08701836119a5565b60e08701519150610100611ae681880184611991565b8701519150610120611afa878201846119b5565b80880151925050610140818786030181880152611b1785846118d3565b945080880151925050610160818786030181880152611b3685846118d3565b945080880151925050610180611b57818801846001600160b01b0319169052565b808801519250506101a0818786030181880152611b7485846118d3565b9450808801519250506101c0818786030181880152611b9385846119c5565b945080880151925050506101e0611bb4818701836001600160a01b03169052565b8601519050610200611bc98682018315159052565b90950151151593019290925250919050565b600080600060408486031215611bf057600080fd5b611bf984611895565b925060208401356001600160401b0380821115611c1557600080fd5b818601915086601f830112611c2957600080fd5b813581811115611c3857600080fd5b876020828501011115611c4a57600080fd5b6020830194508093505050509250925092565b6002811061182757600080fd5b60008060408385031215611c7d57600080fd5b611c8683611895565b91506020830135611c9681611c5d565b809150509250929050565b8035600681106118ac57600080fd5b60008060408385031215611cc357600080fd5b611ccc83611895565b9150611cda60208401611ca1565b90509250929050565b60006101c08284031215611cf657600080fd5b50919050565b60008060408385031215611d0f57600080fd5b82356001600160801b031981168114611d2757600080fd5b915060208301356001600160401b03811115611d4257600080fd5b611d4e85828601611ce3565b9150509250929050565b80356001600160b01b0319811681146118ac57600080fd5b60008060408385031215611d8357600080fd5b611d8c83611895565b9150611cda60208401611d58565b803580151581146118ac57600080fd5b60008060408385031215611dbd57600080fd5b611dc683611895565b9150611cda60208401611d9a565b6020808252825182820181905260009190848201906040850190845b81811015611e155783516001600160401b031683529284019291840191600101611df0565b50909695505050505050565b60008060408385031215611e3457600080fd5b611d2783611895565b60208082526012908201527110dd5cdd1bdb595c881b9bdd08199bdd5b9960721b604082015260600190565b600181811c90821680611e7d57607f821691505b602082108103611cf657634e487b7160e01b600052602260045260246000fd5b6020808252600d908201526c2737ba103a34329037bbb732b960991b604082015260600190565b634e487b7160e01b600052604160045260246000fd5b601f821115611f2457600081815260208120601f850160051c81016020861015611f015750805b601f850160051c820191505b81811015611f2057828155600101611f0d565b5050505b505050565b6001600160401b03831115611f4057611f40611ec4565b611f5483611f4e8354611e69565b83611eda565b6000601f841160018114611f885760008515611f705750838201355b600019600387901b1c1916600186901b178355611fe2565b600083815260209020601f19861690835b82811015611fb95786850135825560209485019460019092019101611f99565b5086821015611fd65760001960f88860031b161c19848701351681555b505060018560011b0183555b5050505050565b60208152816020820152818360408301376000818301604090810191909152601f909201601f19160101919050565b6000808335601e1984360301811261202f57600080fd5b8301803591506001600160401b0382111561204957600080fd5b60200191503681900382131561205e57600080fd5b9250929050565b60006020828403121561207757600080fd5b6118cc82611d58565b60006020828403121561209257600080fd5b81356118cc81611c5d565b6000602082840312156120af57600080fd5b6118cc82611ca1565b6000602082840312156120ca57600080fd5b8135600481106118cc57600080fd5b6000602082840312156120eb57600080fd5b6118cc82611d9a565b6000808335601e1984360301811261210b57600080fd5b8301803591506001600160401b0382111561212557600080fd5b6020019150600581901b360382131561205e57600080fd5b634e487b7160e01b600052603260045260246000fd5b60006001820161217357634e487b7160e01b600052601160045260246000fd5b506001019056fea2646970667358221220ea8f44b91c083b5ee8a2631062eadea95f42899e30e2c2a8e5d468ed70ca8b6b64736f6c63430008140033";

    public static final String FUNC_CREATECUSTOMER = "createCustomer";

    public static final String FUNC_GETCUSTOMER = "getCustomer";

    public static final String FUNC_GETALLCUSTOMERIDS = "getAllCustomerIds";

    public static final String FUNC_GETCIDLISTBYNIK = "getCidListByNik";

    public static final String FUNC_UPDATECUSTOMER = "updateCustomer";

    public static final String FUNC_ADDCIDTOCUSTOMER = "addCidToCustomer";

    public static final String FUNC_UPDATEFULLNAME = "updateFullName";

    public static final String FUNC_UPDATERELIGION = "updateReligion";

    public static final String FUNC_UPDATEMARITALSTATUS = "updateMaritalStatus";

    public static final String FUNC_UPDATEFULLADDRESS = "updateFullAddress";

    public static final String FUNC_UPDATEVALIDUNTIL = "updateValidUntil";

    public static final String FUNC_UPDATEOCCUPATION = "updateOccupation";

    public static final String FUNC_UPDATENATIONALITY = "updateNationality";

    public static final String FUNC_UPDATEACTIVE = "updateActive";

    public static final Event CIDADDED_EVENT = new Event("CidAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event CUSTOMERCREATED_EVENT = new Event("CustomerCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes16>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint64>() {}));
    ;

    public static final Event CUSTOMERUPDATED_EVENT = new Event("CustomerUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes16>(true) {}));
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

    public static List<CidAddedEventResponse> getCidAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CIDADDED_EVENT, transactionReceipt);
        ArrayList<CidAddedEventResponse> responses = new ArrayList<CidAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CidAddedEventResponse typedResponse = new CidAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nik = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.cid = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CidAddedEventResponse getCidAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CIDADDED_EVENT, log);
        CidAddedEventResponse typedResponse = new CidAddedEventResponse();
        typedResponse.log = log;
        typedResponse.nik = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.cid = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CidAddedEventResponse> cidAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCidAddedEventFromLog(log));
    }

    public Flowable<CidAddedEventResponse> cidAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CIDADDED_EVENT));
        return cidAddedEventFlowable(filter);
    }

    public static List<CustomerCreatedEventResponse> getCustomerCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CUSTOMERCREATED_EVENT, transactionReceipt);
        ArrayList<CustomerCreatedEventResponse> responses = new ArrayList<CustomerCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CustomerCreatedEventResponse typedResponse = new CustomerCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.nik = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CustomerCreatedEventResponse getCustomerCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CUSTOMERCREATED_EVENT, log);
        CustomerCreatedEventResponse typedResponse = new CustomerCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.nik = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CustomerUpdatedEventResponse getCustomerUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CUSTOMERUPDATED_EVENT, log);
        CustomerUpdatedEventResponse typedResponse = new CustomerUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> createCustomer(byte[] id, CustomerInput data) {
        final Function function = new Function(
                FUNC_CREATECUSTOMER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(id), 
                data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Customer> getCustomer(BigInteger nik) {
        final Function function = new Function(FUNC_GETCUSTOMER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Customer>() {}));
        return executeRemoteCallSingleValueReturn(function, Customer.class);
    }

    public RemoteFunctionCall<List> getAllCustomerIds() {
        final Function function = new Function(FUNC_GETALLCUSTOMERIDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint64>>() {}));
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

    public RemoteFunctionCall<List> getCidListByNik(BigInteger nik) {
        final Function function = new Function(FUNC_GETCIDLISTBYNIK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik)), 
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

    public RemoteFunctionCall<TransactionReceipt> updateCustomer(BigInteger nik, CustomerInput data) {
        final Function function = new Function(
                FUNC_UPDATECUSTOMER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                data), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCidToCustomer(BigInteger nik, String newCid) {
        final Function function = new Function(
                FUNC_ADDCIDTOCUSTOMER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Utf8String(newCid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateFullName(BigInteger nik, String newName) {
        final Function function = new Function(
                FUNC_UPDATEFULLNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Utf8String(newName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateReligion(BigInteger nik, BigInteger newReligion) {
        final Function function = new Function(
                FUNC_UPDATERELIGION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.generated.Uint8(newReligion)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateMaritalStatus(BigInteger nik, BigInteger newStatus) {
        final Function function = new Function(
                FUNC_UPDATEMARITALSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.generated.Uint8(newStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateFullAddress(BigInteger nik, String newAddress) {
        final Function function = new Function(
                FUNC_UPDATEFULLADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Utf8String(newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateValidUntil(BigInteger nik, byte[] newValidUntil) {
        final Function function = new Function(
                FUNC_UPDATEVALIDUNTIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.generated.Bytes10(newValidUntil)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateOccupation(BigInteger nik, String newOccupation) {
        final Function function = new Function(
                FUNC_UPDATEOCCUPATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Utf8String(newOccupation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateNationality(BigInteger nik, String newNationality) {
        final Function function = new Function(
                FUNC_UPDATENATIONALITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Utf8String(newNationality)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateActive(BigInteger nik, Boolean newActive) {
        final Function function = new Function(
                FUNC_UPDATEACTIVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                new org.web3j.abi.datatypes.Bool(newActive)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static class CustomerInput extends DynamicStruct {
        public BigInteger nik;

        public String fullName;

        public String birthPlace;

        public byte[] birthDate;

        public BigInteger gender;

        public BigInteger religion;

        public BigInteger maritalStatus;

        public BigInteger bloodType;

        public String nationality;

        public String fullAddress;

        public byte[] validUntil;

        public String occupation;

        public List<String> cid;

        public Boolean active;

        public CustomerInput(BigInteger nik, String fullName, String birthPlace, byte[] birthDate, BigInteger gender, BigInteger religion, BigInteger maritalStatus, BigInteger bloodType, String nationality, String fullAddress, byte[] validUntil, String occupation, List<String> cid, Boolean active) {
            super(new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.Utf8String(birthPlace), 
                    new org.web3j.abi.datatypes.generated.Bytes10(birthDate), 
                    new org.web3j.abi.datatypes.generated.Uint8(gender), 
                    new org.web3j.abi.datatypes.generated.Uint8(religion), 
                    new org.web3j.abi.datatypes.generated.Uint8(maritalStatus), 
                    new org.web3j.abi.datatypes.generated.Uint8(bloodType), 
                    new org.web3j.abi.datatypes.Utf8String(nationality), 
                    new org.web3j.abi.datatypes.Utf8String(fullAddress), 
                    new org.web3j.abi.datatypes.generated.Bytes10(validUntil), 
                    new org.web3j.abi.datatypes.Utf8String(occupation), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                            org.web3j.abi.datatypes.Utf8String.class,
                            org.web3j.abi.Utils.typeMap(cid, org.web3j.abi.datatypes.Utf8String.class)), 
                    new org.web3j.abi.datatypes.Bool(active));
            this.nik = nik;
            this.fullName = fullName;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
            this.gender = gender;
            this.religion = religion;
            this.maritalStatus = maritalStatus;
            this.bloodType = bloodType;
            this.nationality = nationality;
            this.fullAddress = fullAddress;
            this.validUntil = validUntil;
            this.occupation = occupation;
            this.cid = cid;
            this.active = active;
        }

        public CustomerInput(Uint64 nik, Utf8String fullName, Utf8String birthPlace, Bytes10 birthDate, Uint8 gender, Uint8 religion, Uint8 maritalStatus, Uint8 bloodType, Utf8String nationality, Utf8String fullAddress, Bytes10 validUntil, Utf8String occupation, DynamicArray<Utf8String> cid, Bool active) {
            super(nik, fullName, birthPlace, birthDate, gender, religion, maritalStatus, bloodType, nationality, fullAddress, validUntil, occupation, cid, active);
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.birthPlace = birthPlace.getValue();
            this.birthDate = birthDate.getValue();
            this.gender = gender.getValue();
            this.religion = religion.getValue();
            this.maritalStatus = maritalStatus.getValue();
            this.bloodType = bloodType.getValue();
            this.nationality = nationality.getValue();
            this.fullAddress = fullAddress.getValue();
            this.validUntil = validUntil.getValue();
            this.occupation = occupation.getValue();
            this.cid = cid.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.active = active.getValue();
        }
    }

    public static class Customer extends DynamicStruct {
        public byte[] id;

        public BigInteger nik;

        public String fullName;

        public String birthPlace;

        public byte[] birthDate;

        public BigInteger gender;

        public BigInteger religion;

        public BigInteger maritalStatus;

        public BigInteger bloodType;

        public String nationality;

        public String fullAddress;

        public byte[] validUntil;

        public String occupation;

        public List<String> cid;

        public String owner;

        public Boolean exists;

        public Boolean active;

        public Customer(byte[] id, BigInteger nik, String fullName, String birthPlace, byte[] birthDate, BigInteger gender, BigInteger religion, BigInteger maritalStatus, BigInteger bloodType, String nationality, String fullAddress, byte[] validUntil, String occupation, List<String> cid, String owner, Boolean exists, Boolean active) {
            super(new org.web3j.abi.datatypes.generated.Bytes16(id), 
                    new org.web3j.abi.datatypes.generated.Uint64(nik), 
                    new org.web3j.abi.datatypes.Utf8String(fullName), 
                    new org.web3j.abi.datatypes.Utf8String(birthPlace), 
                    new org.web3j.abi.datatypes.generated.Bytes10(birthDate), 
                    new org.web3j.abi.datatypes.generated.Uint8(gender), 
                    new org.web3j.abi.datatypes.generated.Uint8(religion), 
                    new org.web3j.abi.datatypes.generated.Uint8(maritalStatus), 
                    new org.web3j.abi.datatypes.generated.Uint8(bloodType), 
                    new org.web3j.abi.datatypes.Utf8String(nationality), 
                    new org.web3j.abi.datatypes.Utf8String(fullAddress), 
                    new org.web3j.abi.datatypes.generated.Bytes10(validUntil), 
                    new org.web3j.abi.datatypes.Utf8String(occupation), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                            org.web3j.abi.datatypes.Utf8String.class,
                            org.web3j.abi.Utils.typeMap(cid, org.web3j.abi.datatypes.Utf8String.class)), 
                    new org.web3j.abi.datatypes.Address(160, owner), 
                    new org.web3j.abi.datatypes.Bool(exists), 
                    new org.web3j.abi.datatypes.Bool(active));
            this.id = id;
            this.nik = nik;
            this.fullName = fullName;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
            this.gender = gender;
            this.religion = religion;
            this.maritalStatus = maritalStatus;
            this.bloodType = bloodType;
            this.nationality = nationality;
            this.fullAddress = fullAddress;
            this.validUntil = validUntil;
            this.occupation = occupation;
            this.cid = cid;
            this.owner = owner;
            this.exists = exists;
            this.active = active;
        }

        public Customer(Bytes16 id, Uint64 nik, Utf8String fullName, Utf8String birthPlace, Bytes10 birthDate, Uint8 gender, Uint8 religion, Uint8 maritalStatus, Uint8 bloodType, Utf8String nationality, Utf8String fullAddress, Bytes10 validUntil, Utf8String occupation, DynamicArray<Utf8String> cid, Address owner, Bool exists, Bool active) {
            super(id, nik, fullName, birthPlace, birthDate, gender, religion, maritalStatus, bloodType, nationality, fullAddress, validUntil, occupation, cid, owner, exists, active);
            this.id = id.getValue();
            this.nik = nik.getValue();
            this.fullName = fullName.getValue();
            this.birthPlace = birthPlace.getValue();
            this.birthDate = birthDate.getValue();
            this.gender = gender.getValue();
            this.religion = religion.getValue();
            this.maritalStatus = maritalStatus.getValue();
            this.bloodType = bloodType.getValue();
            this.nationality = nationality.getValue();
            this.fullAddress = fullAddress.getValue();
            this.validUntil = validUntil.getValue();
            this.occupation = occupation.getValue();
            this.cid = cid.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.owner = owner.getValue();
            this.exists = exists.getValue();
            this.active = active.getValue();
        }
    }

    public static class CidAddedEventResponse extends BaseEventResponse {
        public BigInteger nik;

        public String cid;
    }

    public static class CustomerCreatedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String owner;

        public BigInteger nik;
    }

    public static class CustomerUpdatedEventResponse extends BaseEventResponse {
        public byte[] id;
    }
}
