import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RequestStatusDetailsService } from 'app/entities/request-status-details/request-status-details.service';
import { IRequestStatusDetails, RequestStatusDetails } from 'app/shared/model/request-status-details.model';

describe('Service Tests', () => {
  describe('RequestStatusDetails Service', () => {
    let injector: TestBed;
    let service: RequestStatusDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequestStatusDetails;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RequestStatusDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RequestStatusDetails(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            decisionDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a RequestStatusDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            decisionDate: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            decisionDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .create(new RequestStatusDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a RequestStatusDetails', () => {
        const returnedFromService = Object.assign(
          {
            requestStatusId: 1,
            decisionByName: 'BBBBBB',
            decisionByMudid: 'BBBBBB',
            decisionByEmail: 'BBBBBB',
            decisionDate: currentDate.format(DATE_TIME_FORMAT),
            decisionComments: 'BBBBBB',
            rejectionReason: 'BBBBBB',
            activeFlag: true,
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            decisionDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of RequestStatusDetails', () => {
        const returnedFromService = Object.assign(
          {
            requestStatusId: 1,
            decisionByName: 'BBBBBB',
            decisionByMudid: 'BBBBBB',
            decisionByEmail: 'BBBBBB',
            decisionDate: currentDate.format(DATE_TIME_FORMAT),
            decisionComments: 'BBBBBB',
            rejectionReason: 'BBBBBB',
            activeFlag: true,
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            decisionDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body),
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RequestStatusDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
