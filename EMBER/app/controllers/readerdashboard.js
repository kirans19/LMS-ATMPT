import Controller from '@ember/controller';
import { action } from '@ember/object';
import { tracked } from '@glimmer/tracking';
import CryptoJS from 'crypto-js';

import { inject as service } from '@ember/service';

export default class ReaderdashboardController extends Controller {
  // @service session;

  @service router;
  myVariable = null;

  @tracked bookCatalogue = [];
  @tracked reservedBooks = [];
  @tracked profile = [];
  @tracked history = [];
  @tracked message = '';
  @tracked amount = 0;
  @tracked due = false;
  @tracked res = false;

  @tracked isBookCatalogueDivVisible = false;
  @tracked isReturnBookDivVisible = false;
  @tracked isPayDueDivVisible = false;
  @tracked isProfileVisible = false;

  @action
  async getBookCatalogue() {
    const response = await fetch(
      'http://localhost:8080/LMS_ATMPT/BookCatalogue',
      {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    const data = await response.json();
    this.bookCatalogue = data.bookCatalogue;
    console.log(data.bookCatalogue);
  }

  // @action
  // async getBookCatalogue() {
  //   const response = await fetch(
  //     'http://localhost:8080/LMS_ATMPT/BookCatalogue',
  //     {
  //       method: 'GET',
  //       credentials: 'include',
  //       headers: {
  //         'Content-Type': 'application/json',
  //       },
  //     }
  //   );
  //   const data = await response.json();

  //   try {
  //     // Decrypt the encrypted book catalogue data using AES decryption
  //     const encryptedData = data.encryptedData;
  //     console.log(data.encryptedData);
  //     const key = "00112233445566778899aabbccddeeff"; // Replace with the same secret key used for encryption
  //     const decodedData = encryptedData;
  //     const decryptedData = CryptoJS.AES.decrypt(decodedData, key).toString(CryptoJS.enc.Utf8);
  //     console.log();
  //     console.log(decryptedData);

  //     // Parse the decrypted data as a JSON object and assign it to the bookCatalogue property
  //     const parsedData = JSON.parse(decryptedData);
  //     this.bookCatalogue = parsedData.bookCatalogue;
  //   } catch (e) {

  //   }
  // }
  @action
  async reserveBook(ISBN) {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/reserve', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        // id: this.session.get('id'),
        // id: '111148',
        ISBN: ISBN,
      }),
    });

    const data = await response.json();
    this.message = data.message;

    this.res = true;
    setTimeout(() => {
      this.res = false;
    }, 700);
  }

  @action
  async getReturnBooks() {
    const response = await fetch(
      'http://localhost:8080/LMS_ATMPT/reservedBooks',
      {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
        // body: JSON.stringify({
        //   // id: this.session.get('id'),
        //   id: '111148',
        // }),
      }
    );
    const data = await response.json();
    console.log(data.reservedBooks);
    this.reservedBooks = data.reservedBooks;
  }

  @action
  async returnBooks(ISBN) {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/return', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        // id: this.session.get('id'),
        // id: '111148',
        ISBN: ISBN,
      }),
    });
    const data = await response.json();
    this.message = data.message;

    this.res = true;
    setTimeout(() => {
      this.res = false;
    }, 1000);
    setTimeout(() => {
      this.getReturnBooks();
    }, 1000);
  }

  @action
  async getProfile() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/profile', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      // body: JSON.stringify({
      //   // id:this.session.get('id'),
      //   id: '111148',
      // }),
    });
    const data = await response.json();
    this.profile = data.profile;
    this.history = data.history;
    console.log(data.profile);
    console.log(data.history);
  }

  @action
  async getDue() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/due', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      // body: JSON.stringify({
      //   // id:this.session.get('id'),
      //   id: '111148',
      // }),
    });
    const data = await response.json();
    if (data.amount == 0) {
      this.due = true;
    } else {
      this.amount = data.amount;
    }
  }

  @action
  async Pay() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/pay', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      // body: JSON.stringify({
      //   // id:this.session.get('id'),
      //   id: '111148',
      // }),
    });
    const data = await response.json();
  }

  @action
  async Logout() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/logout', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      // body: JSON.stringify({
      //   // id:this.session.get('id'),
      //   id: '111148',
      // }),
    });
    this.router.transitionTo('/');
  }

  @tracked isSelected = false;

  @action
  toggleSelected() {
    this.isSelected = !this.isSelected;
  }

  @action
  toggleisBookCatalogueDivVisible() {
    this.isBookCatalogueDivVisible = true;
    this.isReturnBookDivVisible = false;
    this.isPayDueDivVisible = false;
    this.isProfileVisible = false;
    console.log(this.isBookCatalogueDivVisible);
  }
  @action
  toggleisReturnBookDivVisible() {
    this.isBookCatalogueDivVisible = false;
    this.isReturnBookDivVisible = true;
    this.isPayDueDivVisible = false;
    this.isProfileVisible = false;
  }
  @action
  toggleisPayDueDivVisible() {
    this.isBookCatalogueDivVisible = false;
    this.isReturnBookDivVisible = false;
    this.isPayDueDivVisible = true;
    this.isProfileVisible = false;
  }
  @action
  toggleisProfileVisible() {
    this.isBookCatalogueDivVisible = false;
    this.isReturnBookDivVisible = false;
    this.isPayDueDivVisible = false;
    this.isProfileVisible = true;
  }
}
