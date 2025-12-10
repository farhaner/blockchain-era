// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract CustomerContract {

    // ===== STRUCTS =====
    struct CustomerCid {
        string identityCopy;
        string residencePermit;
        string incomeProof;
        string businessDocumentCopy;
        string professionalLicense;
        string otherBankCreditCardInfo;
        string emeraldCustomer;
        string taxIdNumber;
    }

    struct Customer {
        string id;
        string nik;
        string fullName;
        string birthDate;
        string gender;
        string nationality;
        string fullAddress;
        string phoneNumber;
        CustomerCid cid;
    }

    // ===== STORAGE BARU =====
    mapping(string => Customer) private customersByNik;
    string[] private customerNiks;

    // ===== EVENTS (Tidak ada perubahan) =====
    event CustomerCreated(string id);
    event CustomerUpdated(string id);

    // ===========================
    // CREATE
    // ===========================
    function createCustomer(Customer memory data) external {
        require(bytes(data.nik).length != 0, "NIK cannot be empty");
        require(bytes(customersByNik[data.nik].nik).length == 0, "Customer (NIK) already exists");

        customersByNik[data.nik] = data;
        customerNiks.push(data.nik);

        emit CustomerCreated(data.id);
    }

    // ===========================
    // UPDATE
    // ===========================
    function updateCustomer(Customer memory data) external {
        require(bytes(data.nik).length != 0, "NIK cannot be empty");
        require(bytes(customersByNik[data.nik].nik).length != 0, "Customer (NIK) not found");

        customersByNik[data.nik] = data;

        emit CustomerUpdated(data.id);
    }

    // ===========================
    // VIEW
    // ===========================
    function getCustomer(string calldata nik) external view returns (Customer memory) {
        require(bytes(nik).length != 0, "NIK cannot be empty");
        require(bytes(customersByNik[nik].nik).length != 0, "Customer (NIK) not found");

        return customersByNik[nik];
    }

    function getAllCustomerNiks() external view returns (string[] memory) {
        return customerNiks;
    }
}