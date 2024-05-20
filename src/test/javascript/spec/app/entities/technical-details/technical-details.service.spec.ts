import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TechnicalDetailsService } from 'app/entities/technical-details/technical-details.service';
import { ITechnicalDetails, TechnicalDetails } from 'app/shared/model/technical-details.model';

describe('Service Tests', () => {
  describe('TechnicalDetails Service', () => {
    let injector: TestBed;
    let service: TechnicalDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITechnicalDetails;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TechnicalDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TechnicalDetails(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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
            targetIngestionStartDate: currentDate.format(DATE_FORMAT),
            targetIngestionEndDate: currentDate.format(DATE_FORMAT),
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

      it('should create a TechnicalDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            targetIngestionStartDate: currentDate.format(DATE_FORMAT),
            targetIngestionEndDate: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            targetIngestionStartDate: currentDate,
            targetIngestionEndDate: currentDate,
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService,
        );
        service
          .create(new TechnicalDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TechnicalDetails', () => {
        const returnedFromService = Object.assign(
          {
            technicalRequestId: 1,
            dataLocationPath: 'BBBBBB',
            dataRefreshFrequency: 'BBBBBB',
            targetIngestionStartDate: currentDate.format(DATE_FORMAT),
            targetIngestionEndDate: currentDate.format(DATE_FORMAT),
            targetPath: 'BBBBBB',
            datasetTypeIngestionRef: 'BBBBBB',
            guestUsersEmail: 'BBBBBB',
            whitelistIpAddresses: 'BBBBBB',
            externalStagingContainerName: 'BBBBBB',
            externalDataSourceLocation: 'BBBBBB',
            gskAccessSourceLocationRef: 'BBBBBB',
            externalSourceSecretKeyName: 'BBBBBB',
            domainRequestId: 'BBBBBB',
            existingDataLocationIdentified: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            targetIngestionStartDate: currentDate,
            targetIngestionEndDate: currentDate,
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

      it('should return a list of TechnicalDetails', () => {
        const returnedFromService = Object.assign(
          {
            technicalRequestId: 1,
            dataLocationPath: 'BBBBBB',
            dataRefreshFrequency: 'BBBBBB',
            targetIngestionStartDate: currentDate.format(DATE_FORMAT),
            targetIngestionEndDate: currentDate.format(DATE_FORMAT),
            targetPath: 'BBBBBB',
            datasetTypeIngestionRef: 'BBBBBB',
            guestUsersEmail: 'BBBBBB',
            whitelistIpAddresses: 'BBBBBB',
            externalStagingContainerName: 'BBBBBB',
            externalDataSourceLocation: 'BBBBBB',
            gskAccessSourceLocationRef: 'BBBBBB',
            externalSourceSecretKeyName: 'BBBBBB',
            domainRequestId: 'BBBBBB',
            existingDataLocationIdentified: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            targetIngestionStartDate: currentDate,
            targetIngestionEndDate: currentDate,
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

      it('should delete a TechnicalDetails', () => {
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
