// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract CustomerContract {

    // ===== ENUMS =====
    enum Gender {LakiLaki, Perempuan}
    enum Religion {Islam, Protestan, Katolik, Hindu, Buddha, Konghucu}
    enum MaritalStatus {Kawin, BelumKawin}
    enum BloodType {A, B, AB, O}

    // ===== STRUCTS =====
    struct CustomerCid {
        string nationalIdentityCard;
        string taxIdentificationNumber;
        string paySlip;
    }

    struct Customer {
        bytes16 id;
        uint64 nik;
        string fullName;
        string birthPlace;
        bytes10 birthDate;
        Gender gender;
        Religion religion;
        MaritalStatus maritalStatus;
        BloodType bloodType;
        string nationality;
        string fullAddress;
        bytes10 validUntil;
        string occupation;
        string[] cid;
        address owner;
        bool exists;
        bool active;
    }

    struct CustomerInput {
        uint64 nik;
        string fullName;
        string birthPlace;
        bytes10 birthDate;
        Gender gender;
        Religion religion;
        MaritalStatus maritalStatus;
        BloodType bloodType;
        string nationality;
        string fullAddress;
        bytes10 validUntil;
        string occupation;
        CustomerCid cid;
        bool active;
    }

    // ===== STORAGE =====
    mapping(uint64 => Customer) private customers;
    uint64[] private customerIds;

    // ===== EVENTS =====
    event CustomerCreated(bytes16 indexed id, address indexed owner, uint64 nik);
    event CustomerUpdated(bytes16 indexed id);
    event CidAdded(uint64 indexed nik, string cid);

    // ===== CREATE =====
    function createCustomer(bytes16 id, CustomerInput calldata data) external returns (bytes16) {
        require(!customers[data.nik].exists, "Customer already exists");

        Customer storage c = customers[data.nik];
        c.id = id;
        c.nik = data.nik;
        c.fullName = data.fullName;
        c.birthPlace = data.birthPlace;
        c.birthDate = data.birthDate;
        c.gender = data.gender;
        c.religion = data.religion;
        c.maritalStatus = data.maritalStatus;
        c.bloodType = data.bloodType;
        c.nationality = data.nationality;
        c.fullAddress = data.fullAddress;
        c.validUntil = data.validUntil;
        c.occupation = data.occupation;
        c.owner = msg.sender;
        c.exists = true;
        c.active = data.active;

        for (uint i = 0; i < data.cid.length; i++) {
            c.cid.push(data.cid[i]);
        }

        customerIds.push(data.nik);
        emit CustomerCreated(id, msg.sender, data.nik);
        return id;
    }

    // ===== READ =====
    function getCustomer(uint64 nik) external view returns (Customer memory) {
        require(customers[nik].exists, "Customer not found");
        return customers[nik];
    }

    function getAllCustomerIds() external view returns (uint64[] memory) {
        return customerIds;
    }

    function getCidListByNik(uint64 nik) external view returns (string[] memory) {
        require(customers[nik].exists, "Customer not found");
        return customers[nik].cid;
    }

    // ===== UPDATE (FULL) =====
    function updateCustomer(uint64 nik, CustomerInput calldata data) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");

        Customer storage c = customers[nik];
        c.fullName = data.fullName;
        c.religion = data.religion;
        c.maritalStatus = data.maritalStatus;
        c.nationality = data.nationality;
        c.fullAddress = data.fullAddress;
        c.validUntil = data.validUntil;
        c.occupation = data.occupation;
        c.active = data.active;

        delete c.cid;
        for (uint i = 0; i < data.cid.length; i++) {
            c.cid.push(data.cid[i]);
        }

        emit CustomerUpdated(c.id);
    }


    // ===== UPDATE (HELPERS) =====
    function addCidToCustomer(uint64 nik, string calldata newCid) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].cid.push(newCid);
        emit CidAdded(nik, newCid);
    }

    function updateFullName(uint64 nik, string calldata newName) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].fullName = newName;
    }

    function updateReligion(uint64 nik, Religion newReligion) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].religion = newReligion;
    }

    function updateMaritalStatus(uint64 nik, MaritalStatus newStatus) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].maritalStatus = newStatus;
    }

    function updateFullAddress(uint64 nik, string calldata newAddress) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].fullAddress = newAddress;
    }

    function updateValidUntil(uint64 nik, bytes10 newValidUntil) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].validUntil = newValidUntil;
    }

    function updateOccupation(uint64 nik, string calldata newOccupation) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].occupation = newOccupation;
    }

    function updateNationality(uint64 nik, string calldata newNationality) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].nationality = newNationality;
    }

    function updateActive(uint64 nik, bool newActive) external {
        require(customers[nik].exists, "Customer not found");
        require(customers[nik].owner == msg.sender, "Not the owner");
        customers[nik].active = newActive;
    }
}