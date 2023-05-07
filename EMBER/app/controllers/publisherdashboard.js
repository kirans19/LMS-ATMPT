import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class PublisherdashboardController extends Controller {
  @service router;
  @tracked readers = [];
  @tracked mybooks = [];
  @tracked profile = [];
  @tracked history = [];

  @tracked isProfileVisible = false;
  @tracked isPublishedBooksVisible = false;
  @tracked isReadersVisible = false;

  @action
  async Logout() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/logout', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    this.router.transitionTo('/');
  }

  @action
  async getReaders() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/readers', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const data = await response.json();
    this.readers = data.readers;
    console.log(this.readers);
  }

  @action
  async getBooks() {
    const response = await fetch('http://localhost:8080/LMS_ATMPT/mybooks', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const data = await response.json();
    this.mybooks = data.books;
    console.log(data.books);
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
  togglePublishedBooksVisible() {
    this.isProfileVisible = false;
    this.isPublishedBooksVisible = true;
    this.isReadersVisible = false;
  }

  @action
  toggleReadersVisible() {
    this.isProfileVisible = false;
    this.isPublishedBooksVisible = false;
    this.isReadersVisible = true;
  }

  @action
  toggleisProfileVisible() {
    this.isProfileVisible = true;
    this.isPublishedBooksVisible = false;
    this.isReadersVisible = false;
  }

  @action
  toggleReaderMode() {
    this.isProfileVisible = false;
    this.isPublishedBooksVisible = false;
    this.isReadersVisible = false;
  }
}
